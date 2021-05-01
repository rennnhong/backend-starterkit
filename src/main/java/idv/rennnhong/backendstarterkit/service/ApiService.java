package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.backendstarterkit.service.dto.ApiEditDto;
import idv.rennnhong.backendstarterkit.service.dto.ApiDto;
import org.springframework.http.HttpMethod;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface ApiService {


//    List<ApiDto> getAllApiByRoles(List<UUID> roleIds);
//
//    List<ApiDto> getAllApiByRole(UUID roleId, String url);

    Collection<ApiDto> getAll();

    ApiDto getById(UUID id);

    ApiDto getRestFulApi(String url, HttpMethod httpMethod);

    ApiDto save(ApiDto apiDto);

    ApiDto update(UUID id, ApiEditDto apiEditDto);

    void delete(UUID id);

    boolean isExist(UUID id);

    boolean isAccessibleByRoles(List<UUID> roleIds, ApiDto apiDto);
}
