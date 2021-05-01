package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.backendstarterkit.service.dto.PermissionEditDto;
import idv.rennnhong.backendstarterkit.service.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.service.dto.RoleDto;
import idv.rennnhong.common.query.PageableResult;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface PermissionService {

    Collection<PermissionDto> getAll();

    PermissionDto getById(UUID id);

    PermissionDto save(PermissionDto permissionDto);

    PermissionDto update(UUID id, PermissionEditDto permissionEditDto);

    void delete(UUID id);

    boolean isExist(UUID id);

    boolean isLastLayer(UUID id);

    PageableResult<PermissionDto> pageAll(Integer pageNumber, Integer rowsPerPage);

    Set<PermissionDto> getAllPermissions(UUID roleId);

    Set<PermissionDto> getPermissionsByRoles(Collection<RoleDto> roles);

}
