package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import idv.rennnhong.backendstarterkit.service.dto.PermissionEditDto;
import idv.rennnhong.backendstarterkit.service.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.service.dto.RoleDto;
import idv.rennnhong.backendstarterkit.service.mapper.PermissionMapper;
import idv.rennnhong.backendstarterkit.service.mapper.RoleMapper;
import idv.rennnhong.backendstarterkit.exception.ExceptionFactory;
import idv.rennnhong.backendstarterkit.exception.ExceptionType;
import idv.rennnhong.backendstarterkit.entity.Permission;
import idv.rennnhong.backendstarterkit.entity.Role;
import idv.rennnhong.backendstarterkit.repository.PermissionRepository;
import idv.rennnhong.backendstarterkit.repository.RoleRepository;
import idv.rennnhong.backendstarterkit.service.PermissionService;
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

import static idv.rennnhong.backendstarterkit.exception.GroupType.PERMISSION;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

    final PermissionRepository permissionRepository;

    final PermissionMapper permissionMapper;

    RoleRepository roleRepository;

    RoleMapper roleMapper;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public PermissionServiceImpl(PermissionRepository permissionRepository, PermissionMapper permissionMapper) {
        this.permissionRepository = permissionRepository;
        this.permissionMapper = permissionMapper;
    }


    @Override
    public Collection<PermissionDto> getAll() {
        List<Permission> permissions = permissionRepository.findAll();
        return permissionMapper.toDto(permissions);
    }

    @Override
    public PermissionDto getById(UUID id) {
        Permission permission = permissionRepository.findById(id).get();
        return permissionMapper.toDto(permission);
    }

    @Override
    public PermissionDto save(PermissionDto permissionDto) {
        Permission entity = permissionMapper.createEntity(permissionDto);

        Permission parent = permissionRepository.findById(permissionDto.getParentId()).get();
        entity.setParent(parent);

        permissionRepository.save(entity);
        return permissionMapper.toDto(entity);
    }

    @Override
    public PermissionDto update(UUID id, PermissionEditDto permissionEditDto) {
        Permission permission = permissionRepository.findById(id).get();
        permissionMapper.updateEntity(permission, permissionEditDto);
        return permissionMapper.toDto(permission);
    }

    @Override
    public void delete(UUID id) {
        Optional<Permission> optionalPermission = permissionRepository.findById(id);
        Permission permission = optionalPermission.orElseThrow(() ->
                ExceptionFactory.newException(PERMISSION, ExceptionType.ENTITY_NOT_FOUND, id.toString())
        );
        permissionRepository.delete(permission);
    }

    @Override
    public boolean isExist(UUID id) {
        return permissionRepository.existsById(id);
    }

    @Override
    public boolean isLastLayer(UUID id) {
        //若不為任何Page的pid，即為葉子節點
        return !permissionRepository.existsByParentId(id);
    }

    @Override
    public PageableResult<PermissionDto> pageAll(Integer pageNumber, Integer rowsPerPage) {
        QueryParameter qp = new QueryParameter()
                .addPageNumber(pageNumber)
                .addRowsPerPage(rowsPerPage)
                .build();

        Page<Permission> resultPage = permissionRepository.findAll(
                PageRequest.of(qp.getPageOffset(), qp.getPageLimit()));

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
        Role role = roleRepository.findById(roleId).get();
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
