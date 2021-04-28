package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.backendstarterkit.controller.request.api.CreateApiRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.api.UpdateApiRequestDto;
import idv.rennnhong.backendstarterkit.dto.ApiDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import org.springframework.http.HttpMethod;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ApiService {


//    List<ApiDto> getAllApiByRoles(List<UUID> roleIds);
//
//    List<ApiDto> getAllApiByRole(UUID roleId, String url);

    Collection<ApiDto> getAll();

    ApiDto getById(UUID id);

    ApiDto getRestFulApi(String url, HttpMethod httpMethod);

    ApiDto save(CreateApiRequestDto createApiRequestDto);

    ApiDto update(UUID id, UpdateApiRequestDto updateApiRequestDto);

    void delete(UUID id);

    boolean isExist(UUID id);

    boolean isAccessibleByRoles(List<UUID> roleIds, ApiDto apiDto);
}
