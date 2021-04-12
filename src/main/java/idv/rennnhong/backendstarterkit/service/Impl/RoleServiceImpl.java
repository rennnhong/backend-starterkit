package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import idv.rennnhong.backendstarterkit.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.RolePermissionDto;
import idv.rennnhong.backendstarterkit.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.mapper.RoleMapper;
import idv.rennnhong.backendstarterkit.model.dao.ActionDao;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.dao.UserDao;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.RolePermission;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.query.PageableResultImpl;
import idv.rennnhong.common.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class RoleServiceImpl implements RoleService {

    final RoleDao roleDao;

    final RoleMapper roleMapper;

    PermissionDao permissionDao;

    ActionDao actionDao;

    UserDao userDao;

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Autowired
    public void setActionDao(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public RoleServiceImpl(RoleDao roleDao, RoleMapper roleMapper) {
//        super(roleDao, roleMapper);
        this.roleDao = roleDao;
        this.roleMapper = roleMapper;
    }


    @Override
    public Collection<RoleDto> getAll() {
        List<Role> roles = roleDao.findAll();
        return Lists.newArrayList(roleMapper.toDto(roles));
    }

    @Override
    public RoleDto getById(UUID id) {
        Role role = roleDao.findById(id).get();
        return roleMapper.toDto(role);
    }

    @Override
    public RoleDto save(CreateRoleRequestDto createRoleRequestDto) {
        Role role = roleMapper.createEntity(createRoleRequestDto);
        Role savedRole = roleDao.save(role);
        return roleMapper.toDto(savedRole);
    }

    @Override
    public RoleDto update(UUID id, UpdateRoleRequestDto updateRoleRequestDto) {
        Role role = roleDao.findById(id).get();
        roleMapper.updateEntity(role, updateRoleRequestDto);

        Role updatedRole = roleDao.save(role);
        return roleMapper.toDto(updatedRole);
    }

    @Override
    public void delete(UUID id) {
        roleDao.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return roleDao.existsById(id);
    }

    @Override
    public RoleDto updateRolePage(UUID roleId, UUID pageId, List<UUID> actionIds) {
        Role role = roleDao.findById(roleId).get();
        Permission permission = permissionDao.findById(pageId).get();
        Set<RolePermission> rolePermissions = role.getRolePermissions();
        rolePermissions.clear();

        actionIds.stream()
                .forEach(actionId -> {
                    rolePermissions
                            .add(new RolePermission(permission, actionDao.findById(actionId).get()));
                });
        Role updatedRole = roleDao.save(role);

        return roleMapper.toDto(updatedRole);
    }


    @Override
    public PageableResult<RoleDto> pageAll(Integer pageNumber, Integer rowsPerPage) {
        QueryParameter qp = new QueryParameter()
                .addPageNumber(pageNumber)
                .addRowsPerPage(rowsPerPage)
                .build();

        Page<Role> resultPage = roleDao.findAll(
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
        User user = userDao.findById(userId).get();
        Set<Role> roles = user.getRoles();
        return ImmutableSet.copyOf(roleMapper.toDto(roles));
    }

    @Override
    public boolean isRoleReferenced(UUID id) {
        Role role = roleDao.findById(id).get();
        return role.getUsers().size() > 0;
    }

//    @Override
//    public List<ApiDTO> getAllApiByRole(RoleDTO role, String url, String httpMethod) {
//        Role roleEntity = roleDao.findById(role.getId()).get();
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
