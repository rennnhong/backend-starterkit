package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import idv.rennnhong.common.BaseServiceImpl;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.query.PageableResultImpl;
import idv.rennnhong.common.query.QueryParameter;
import idv.rennnhong.backendstarterkit.model.dao.ActionDao;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.dao.UserDao;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.RolePermission;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.mapper.RoleMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends BaseServiceImpl<RoleDto, Role, UUID> implements RoleService {

    final RoleDao roleDao;

    final PermissionDao permissionDao;

    final ActionDao actionDao;

    final UserDao userDao;

    final RoleMapper roleMapper;


    @Autowired
    public RoleServiceImpl(RoleDao roleDao, PermissionDao permissionDao, ActionDao actionDao,
                           RoleMapper roleMapper, UserDao userDao) {
        super(roleDao, roleMapper);
        this.roleDao = (RoleDao) super.baseDao;
        this.permissionDao = permissionDao;
        this.actionDao = actionDao;
        this.userDao = userDao;
        this.roleMapper = roleMapper;
    }

//    @Override
//    public KdQueryResult<RoleDTO> getBySearch(SearchRoleDto searchRoleDto) {
//        return null;
//    }

    @Override
    public RoleDto updateRolePage(String roleId, String pageId, String[] actionIds) {
        Role role = roleDao.findById(UUID.fromString(roleId)).get();
        Permission permission = permissionDao.findById(UUID.fromString(pageId)).get();
        Set<RolePermission> rolePermissions = role.getRolePermissions();
        rolePermissions.clear();

        Arrays.stream(actionIds)
            .forEach(actionId -> {
                rolePermissions
                    .add(new RolePermission(permission, actionDao.findById(UUID.fromString(actionId)).get()));
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
    public Set<RoleDto> getRolesByUserId(String userId) {
        User user = userDao.findById(UUID.fromString(userId)).get();
        Set<Role> roles = user.getRoles();
        return ImmutableSet.copyOf(roleMapper.toDto(roles));
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
