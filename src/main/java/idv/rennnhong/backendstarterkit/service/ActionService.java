package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.backendstarterkit.service.dto.ActionEditDto;
import idv.rennnhong.backendstarterkit.service.dto.ActionDto;
import idv.rennnhong.backendstarterkit.entity.Permission;
import idv.rennnhong.common.query.PageableResult;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

public interface ActionService {

    Collection<ActionDto> getAll(UUID permissionId);

    ActionDto getById(UUID id);

    ActionDto save(UUID permissionId, ActionDto actionDto);

    ActionDto update(UUID id, UUID permissionId, ActionEditDto actionEditDto);

    void delete(UUID id);

    boolean isExist(UUID id);

    PageableResult<ActionDto> pageAllByPermission(Permission permission, Integer pageNumber, Integer rowsPerPage);

    Set<ActionDto> getActionsByPermissionId(String permissionId);


}
