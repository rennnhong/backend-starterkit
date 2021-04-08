package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.common.BaseService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.dto.ActionDto;

import java.util.Set;
import java.util.UUID;

public interface ActionService extends BaseService<ActionDto, UUID> {


    PageableResult<ActionDto> pageAllByPermission(Permission permission, Integer pageNumber, Integer rowsPerPage);

    Set<ActionDto> getActionsByPermissionId(String permissionId);


}
