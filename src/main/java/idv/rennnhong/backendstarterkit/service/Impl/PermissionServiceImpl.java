package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import idv.rennnhong.common.BaseServiceImpl;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.query.PageableResultImpl;
import idv.rennnhong.common.query.QueryParameter;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.mapper.PermissionMapper;
import idv.rennnhong.backendstarterkit.dto.mapper.RoleMapper;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.RolePermission;
import idv.rennnhong.backendstarterkit.service.PermissionService;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<PermissionDto, Permission, UUID> implements
    PermissionService {

    final PermissionDao permissionDao;

    final PermissionMapper permissionMapper;


    RoleDao roleDao;

    @Autowired
    RoleMapper roleMapper;

    public PermissionServiceImpl setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
        return this;
    }

    @Autowired
    public PermissionServiceImpl(PermissionDao permissionDao, PermissionMapper permissionMapper) {
        super(permissionDao, permissionMapper);
        this.permissionDao = (PermissionDao) super.baseDao;
        this.permissionMapper = permissionMapper;
    }

    @Override
    public boolean isLastLayer(String id) {
        //若不為任何Page的pid，即為葉子節點
        return !permissionDao.existsByParentId(UUID.fromString(id));
    }

    @Override
    public PageableResult<PermissionDto> pageAll(Integer pageNumber, Integer rowsPerPage) {
        QueryParameter qp = new QueryParameter()
            .addPageNumber(pageNumber)
            .addRowsPerPage(rowsPerPage)
            .build();

        Page<Permission> resultPage = permissionDao.findAll(
            PageRequest.of(qp.getPageOffset(), qp.getPageOffset()));

        List<PermissionDto> dtos = ImmutableList
            .copyOf(permissionMapper.toDto(resultPage.getContent()));

        return new PageableResultImpl(
            qp.getPageLimit(),
            qp.getPageNumber(),
            resultPage.getTotalPages(),
            resultPage.getTotalElements(),
            dtos);
    }

    @Override
    public Set<PermissionDto> getAllPermissions(String roleId) {
        Role role = roleDao.findById(UUID.fromString(roleId)).get();
        Set<Permission> collect = role.getRolePermissions()
            .stream()
            .map(rolePermission -> rolePermission.getPermission())
            .collect(Collectors.toSet());
        return ImmutableSet.copyOf(permissionMapper.toDto(collect));
    }

    @Override
    public Set<PermissionDto> getPermissionsByRoles(Collection<RoleDto> roles) {
        Collection<Role> roleEntities = roleMapper.toEntity(roles);
        Set<RolePermission> rolePermissions = roleEntities.stream()
            .map(role -> role.getRolePermissions())
            .reduce((resultSet, set) -> {
                resultSet.addAll(set);
                return resultSet;
            }).get();

        Set<Permission> permissions = rolePermissions.stream()
            .map(rolePermission -> rolePermission.getPermission())
            .collect(Collectors.toSet());

        return ImmutableSet.copyOf(permissionMapper.toDto(permissions));
    }
}
