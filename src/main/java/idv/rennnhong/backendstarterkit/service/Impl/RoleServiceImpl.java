package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import idv.rennnhong.backendstarterkit.web.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.web.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.mapper.RoleMapper;
import idv.rennnhong.backendstarterkit.exception.ExceptionFactory;
import idv.rennnhong.backendstarterkit.exception.ExceptionType;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.RolePermission;
import idv.rennnhong.backendstarterkit.model.entity.User;
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
    public RoleDto save(CreateRoleRequestDto createRoleRequestDto) {
        Role role = roleMapper.createEntity(createRoleRequestDto);
        Role savedRole = roleRepository.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto update(UUID id, UpdateRoleRequestDto updateRoleRequestDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        Role role = optionalRole.orElseThrow(() ->
                ExceptionFactory.newException(ROLE, ExceptionType.ENTITY_NOT_FOUND, id.toString())
        );

        roleMapper.updateEntity(role, updateRoleRequestDto);

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
        這邊Hibernate會自動用邏輯刪除的查詢方式查出users，delete=1
        就不會被查出來，所以不用擔心因使用者實際還存在資料庫而刪不掉role的問題
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

        //取得該角色所有的RolePermission
        Set<RolePermission> roleAllPermissions = role.getRolePermissions();

        /*
         篩選出role針對此permission的所有操作，若有n個操作則會有n筆資料
         */
        Set<RolePermission> targetRolePermissions = roleAllPermissions
                .stream()
                .filter(rolePermission -> Objects.equals(rolePermission.getPermission(), permission))
                .collect(Collectors.toSet());

        //先清掉對此permission的所有操作
        roleAllPermissions.removeAll(targetRolePermissions);

        //重新新增對此permission的操作
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
//    //此role是否被任何user使用
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
