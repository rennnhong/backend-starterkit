package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.common.query.PageableResult;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface ActionService {

    Collection<ActionDto> getAll(UUID permissionId);

    ActionDto getById(UUID id);

    ActionDto save(CreateActionRequestDto createActionRequestDto);

    ActionDto update(UUID id, UUID permissionId, UpdateActionRequestDto updateActionRequestDto);

    void delete(UUID id);

    boolean isExist(UUID id);

    PageableResult<ActionDto> pageAllByPermission(Permission permission, Integer pageNumber, Integer rowsPerPage);

    Set<ActionDto> getActionsByPermissionId(String permissionId);


}
