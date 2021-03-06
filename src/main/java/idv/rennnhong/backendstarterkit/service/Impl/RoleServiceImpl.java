package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import idv.rennnhong.backendstarterkit.service.dto.RoleEditDto;
import idv.rennnhong.backendstarterkit.service.dto.RoleDto;
import idv.rennnhong.backendstarterkit.service.mapper.RoleMapper;
import idv.rennnhong.backendstarterkit.exception.ExceptionFactory;
import idv.rennnhong.backendstarterkit.exception.ExceptionType;
import idv.rennnhong.backendstarterkit.entity.Permission;
import idv.rennnhong.backendstarterkit.entity.Role;
import idv.rennnhong.backendstarterkit.entity.RolePermission;
import idv.rennnhong.backendstarterkit.entity.User;
import idv.rennnhong.backendstarterkit.repository.ActionRepository;
import idv.rennnhong.backendstarterkit.repository.PermissionRepository;
import idv.rennnhong.backendstarterkit.repository.RoleRepository;
import idv.rennnhong.backendstarterkit.repository.UserRepository;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.query.PageableResultImpl;
import idv.rennnhong.common.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

import static idv.rennnhong.backendstarterkit.exception.GroupType.*;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    final RoleRepository roleRepository;

    final RoleMapper roleMapper;

    PermissionRepository permissionRepository;

    ActionRepository actionRepository;

    UserRepository userRepository;

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public void setActionRepository(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }


    @Override
    public Collection<RoleDto> getAll() {
        List<Role> roles = roleRepository.findAll();
        return Lists.newArrayList(roleMapper.toDto(roles));
    }

    @Override
    public RoleDto getById(UUID id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() ->
                ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_NOT_FOUND, id.toString())
        );
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDto save(RoleDto roleDto) {
        Role role = roleMapper.createEntity(roleDto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto update(UUID id, RoleEditDto roleEditDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() ->
                ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_NOT_FOUND, id.toString())
        );

        roleMapper.updateEntity(role, roleEditDto);

        Role updatedRole = roleRepository.save(role);
        return roleMapper.toDto(updatedRole);
    }

    @Override
    public void delete(UUID id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() ->
                ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_NOT_FOUND, id.toString())
        );

        /*
        ??????Hibernate?????????????????????????????????????????????users???delete=1
        ??????????????????????????????????????????????????????????????????????????????????????????role?????????
         */
        if (role.getUsers().size() > 0)
            throw ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_EXIST_RELATED, id.toString());

        roleRepository.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return roleRepository.existsById(id);
    }

    @Override
    public RoleDto updateRolePermission(UUID roleId, UUID permissionId, List<UUID> actionIds) {
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        Role role = optionalRole.orElseThrow(() ->
                ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_NOT_FOUND, roleId.toString())
        );

        Optional<Permission> optionalPermission = permissionRepository.findById(permissionId);
        Permission permission = optionalPermission.orElseThrow(() ->
                ExceptionFactory.newException(PERMISSION, ExceptionType.ENTITY_NOT_FOUND, permissionId.toString())
        );

        //????????????????????????RolePermission
        Set<RolePermission> roleAllPermissions = role.getRolePermissions();

        /*
         ?????????role?????????permission????????????????????????n??????????????????n?????????
         */
        Set<RolePermission> targetRolePermissions = roleAllPermissions
                .stream()
                .filter(rolePermission -> Objects.equals(rolePermission.getPermission(), permission))
                .collect(Collectors.toSet());

        //???????????????permission???????????????
        roleAllPermissions.removeAll(targetRolePermissions);

        //??????????????????permission?????????
        actionIds.stream()
                .forEach(actionId -> {
                    roleAllPermissions
                            .add(new RolePermission(permission, actionRepository.findById(actionId).get()));
                });
        Role updatedRole = roleRepository.save(role);

        return roleMapper.toDto(updatedRole);
    }


    @Override
    public PageableResult<RoleDto> pageAll(Integer pageNumber, Integer rowsPerPage) {
        QueryParameter qp = new QueryParameter()
                .addPageNumber(pageNumber)
                .addRowsPerPage(rowsPerPage)
                .build();

        Page<Role> resultPage = roleRepository.findAll(
                PageRequest.of(qp.getPageOffset(), qp.getPageLimit()));

        List<RoleDto> roleDtos = ImmutableList.copyOf(roleMapper.toDto(resultPage.getContent()));

        return new PageableResultImpl(
                qp.getPageLimit(),
                qp.getPageNumber(),
                resultPage.getTotalPages(),
                resultPage.getTotalElements(),
                roleDtos);
    }

    @Override
    public Set<RoleDto> getRolesByUserId(UUID userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElseThrow(() ->
                ExceptionFactory.newException(USER, ExceptionType.ENTITY_NOT_FOUND, userId.toString())
        );
        Set<Role> roles = user.getRoles();
        return ImmutableSet.copyOf(roleMapper.toDto(roles));
    }

    @Override
    public Set<RoleDto> getRoleByCodes(Set<String> code) {
        Set<Role> roles = roleRepository.findAllByCodeIn(code);
        return ImmutableSet.copyOf(roleMapper.toDto(roles));
    }

//    @Override
//    //???role???????????????user??????
//    public boolean isRoleReferenced(UUID id) {
//        Optional<Role> optionalRole = roleRepository.findById(id);
//        Role role = optionalRole.orElseThrow(() ->
//                ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_NOT_FOUND, id.toString())
//        );
//
//        if(role.getUsers().size() > 0)
//
//
//        return role.getUsers().size() > 0;
//    }

//    @Override
//    public List<ApiDTO> getAllApiByRole(RoleDTO role, String url, String httpMethod) {
//        Role roleEntity = roleRepository.findById(role.getId()).get();
//        Set<RolePermission> rolePermissions = roleEntity.getRolePermissions();
//
//        List<Api> apiList = rolePermissions.stream()
//            .map(rolePermission -> rolePermission.getAction().getApi())
//            .filter(item -> url.matches(item.getUrl() + "/.*"))
//            .collect(Collectors.toList());
//
//        return roleMapper.;
//    }
//
//    @Override
//    public List<ApiDTO> getAllApiByRoles(Set<RoleDTO> roles, String url, String httpMethod) {
//        ArrayList<Api> apiList = Lists.newArrayList();
//
////        List<List<Api>> roleApiList = roles
////            .stream()
////            .map(role -> getAllApiByRole(role, url, httpMethod))
////            .collect(Collectors.toList());
////
////        roleApiList
////            .stream()
////            .forEach(x -> apiList.addAll(x));
////
////        return apiList;
//        return null;
//    }
}
