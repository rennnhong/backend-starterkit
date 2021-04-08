package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.common.BaseService;
import idv.rennnhong.backendstarterkit.dto.ApiDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ApiService extends BaseService<ApiDto, UUID> {


    List<ApiDto> getAllApiByRoles(Set<RoleDto> roles, String url, String httpMethod);

    List<ApiDto> getAllApiByRole(RoleDto role, String url, String httpMethod);


}
