package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.backendstarterkit.web.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.web.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.common.query.PageableResult;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleService {

    Collection<RoleDto> getAll();

    RoleDto getById(UUID id);

    RoleDto save(CreateRoleRequestDto createRoleRequestDto);

    RoleDto update(UUID id, UpdateRoleRequestDto updateRoleRequestDto);

    void delete(UUID id);

    boolean isExist(UUID id);

//    RoleDto updateRolePermission(UUID roleId, List<RolePermissionDto> rolePermissionDtos);
    RoleDto updateRolePermission(UUID roleId, UUID permissionId, List<UUID> actionIds);

    PageableResult<RoleDto> pageAll(Integer pageNumber, Integer rowsPerPage);

    Set<RoleDto> getRolesByUserId(UUID userId);

//    boolean isRoleReferenced(UUID id);

    Set<RoleDto> getRoleByCodes(Set<String> code);

}
