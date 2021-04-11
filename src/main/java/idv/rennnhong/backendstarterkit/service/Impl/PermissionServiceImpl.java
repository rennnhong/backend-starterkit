package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import idv.rennnhong.backendstarterkit.controller.request.permission.CreatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.UpdatePermissionRequestDto;
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
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl implements PermissionService {

    final PermissionDao permissionDao;

    final PermissionMapper permissionMapper;

    RoleDao roleDao;

    RoleMapper roleMapper;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public PermissionServiceImpl(PermissionDao permissionDao, PermissionMapper permissionMapper) {
        this.permissionDao = permissionDao;
        this.permissionMapper = permissionMapper;
    }


    @Override
    public Collection<PermissionDto> getAll() {
        List<Permission> permissions = permissionDao.findAll();
        return permissionMapper.toDto(permissions);
    }

    @Override
    public PermissionDto getById(UUID id) {
        Permission permission = permissionDao.findById(id).get();
        return permissionMapper.toDto(permission);
    }

    @Override
    public PermissionDto save(CreatePermissionRequestDto createPermissionRequestDto) {
        Permission entity = permissionMapper.createEntity(createPermissionRequestDto);

        Permission parent = permissionDao.findById(createPermissionRequestDto.getParentId()).get();
        entity.setParent(parent);

        permissionDao.save(entity);
        return permissionMapper.toDto(entity);
    }

    @Override
    public PermissionDto update(UUID id, UpdatePermissionRequestDto updatePermissionRequestDto) {
        Permission permission = permissionDao.findById(id).get();
        permissionMapper.updateEntity(permission, updatePermissionRequestDto);
        return permissionMapper.toDto(permission);
    }

    @Override
    public void delete(UUID id) {
        permissionDao.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return permissionDao.existsById(id);
    }

    @Override
    public boolean isLastLayer(UUID id) {
        //若不為任何Page的pid，即為葉子節點
        return !permissionDao.existsByParentId(id);
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
    public Set<PermissionDto> getAllPermissions(UUID roleId) {
        Role role = roleDao.findById(roleId).get();
        Set<Permission> collect = role.getRolePermissions()
                .stream()
                .map(rolePermission -> rolePermission.getPermission())
                .collect(Collectors.toSet());
        return ImmutableSet.copyOf(permissionMapper.toDto(collect));
    }

    @Override
    public Set<PermissionDto> getPermissionsByRoles(Collection<RoleDto> roles) {
//        Collection<Role> roleEntities = roleMapper.toEntity(roles);
//        Set<RolePermission> rolePermissions = roleEntities.stream()
//                .map(role -> role.getRolePermissions())
//                .reduce((resultSet, set) -> {
//                    resultSet.addAll(set);
//                    return resultSet;
//                }).get();
//
//        Set<Permission> permissions = rolePermissions.stream()
//                .map(rolePermission -> rolePermission.getPermission())
//                .collect(Collectors.toSet());
//
//        return ImmutableSet.copyOf(permissionMapper.toDto(permissions));
        return null;
    }
}
