package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.common.BaseService;
import java.util.Set;
import java.util.UUID;

public interface RoleService extends BaseService<RoleDto, UUID> {

    RoleDto updateRolePage(String roleId, String pageId, String[] actionIds);

    PageableResult<RoleDto> pageAll(Integer pageNumber, Integer rowsPerPage);

    Set<RoleDto> getRolesByUserId(String userId);

}
