package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.common.BaseService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface PermissionService extends BaseService<PermissionDto, UUID> {


    boolean isLastLayer(String id);

    PageableResult<PermissionDto> pageAll(Integer pageNumber, Integer rowsPerPage);

    Set<PermissionDto> getAllPermissions(String roleId);

    Set<PermissionDto> getPermissionsByRoles(Collection<RoleDto> roles);

}
