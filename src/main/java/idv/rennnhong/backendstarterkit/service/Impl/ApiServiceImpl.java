package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.Sets;
import idv.rennnhong.backendstarterkit.service.dto.ApiEditDto;
import idv.rennnhong.backendstarterkit.service.dto.ApiDto;
import idv.rennnhong.backendstarterkit.service.mapper.ApiMapper;
import idv.rennnhong.backendstarterkit.entity.Api;
import idv.rennnhong.backendstarterkit.entity.Role;
import idv.rennnhong.backendstarterkit.entity.RolePermission;
import idv.rennnhong.backendstarterkit.repository.ApiRepository;
import idv.rennnhong.backendstarterkit.repository.RoleRepository;
import idv.rennnhong.backendstarterkit.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApiServiceImpl implements ApiService {

    final ApiRepository apiRepository;

    final ApiMapper apiMapper;

    RoleRepository roleRepository;

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public ApiServiceImpl(ApiRepository apiRepository, ApiMapper apiMapper) {
        this.apiRepository = apiRepository;
        this.apiMapper = apiMapper;
    }

//    @Override
//    public List<ApiDto> getAllApiByRole(UUID roleId, String url, String httpMethod) {
//        Role roleEntity = roleRepository.findById(roleId).get();
//        Set<RolePermission> rolePermissions = roleEntity.getRolePermissions();
//
////        List<Api> apiList = rolePermissions.stream()
////            .map(rolePermission -> rolePermission.getAction().getApi())
////            .filter(item -> url.matches(item.getUrl() + "/.*"))
////            .collect(Collectors.toList());
//        return ImmutableList.copyOf(apiMapper.toDto(getApiList(rolePermissions, url)));
//    }

    @Override
    public Collection<ApiDto> getAll() {
        List<Api> apis = apiRepository.findAll();
        return apiMapper.toDto(apis);
    }

    @Override
    public ApiDto getById(UUID id) {
        Api api = apiRepository.findById(id).get();
        return apiMapper.toDto(api);
    }

    @Override
    public ApiDto getRestFulApi(String url, HttpMethod httpMethod) {
        //todo *處理特殊路徑
        String prefix = "/api";
        String apiUrl = prefix + url;
        Optional<Api> optionalApi = apiRepository.findByUrlAndHttpMethod(apiUrl, httpMethod.name());
        Api api = optionalApi.get();
        return apiMapper.toDto(api);
    }

    @Override
    public ApiDto save(ApiDto apiDto) {
        Api entity = apiMapper.createEntity(apiDto);
        apiRepository.save(entity);
        return apiMapper.toDto(entity);
    }

    @Override
    public ApiDto update(UUID id, ApiEditDto apiEditDto) {
        Api api = apiRepository.findById(id).get();
        apiMapper.updateEntity(api, apiEditDto);
        return apiMapper.toDto(api);
    }

    @Override
    public void delete(UUID id) {
        apiRepository.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return apiRepository.existsById(id);
    }

    @Override
    public boolean isAccessibleByRoles(List<UUID> roleIds, ApiDto apiDto) {
        Api api = apiMapper.toEntity(apiDto);
        Set<Api> apiSet = getAllApiByRoles(roleIds);
        return apiSet.contains(api);
    }


    private Set<Api> getAllApiByRoles(List<UUID> roleIds) {

        List<Role> roleEntities = roleRepository.findAllByIdIn(roleIds);

        List<Set<RolePermission>> collect = roleEntities.stream().map(roleEntity -> roleEntity.getRolePermissions())
                .collect(Collectors.toList());

        Set<Api> accessibleApi = Sets.newHashSet();
        for (Set<RolePermission> rolePermissions : collect) {
            Set<Api> apiSet = rolePermissions.stream().map(rolePermission -> rolePermission.getAction().getApi()).collect(Collectors.toSet());
            accessibleApi.addAll(apiSet);
        }

        return accessibleApi;
    }

//    private List<Api> getApiList(Set<RolePermission> rolePermissions, String url) {
//        return rolePermissions.stream()
//                .map(rolePermission -> rolePermission.getAction().getApi())
//                .filter(item -> url.matches(item.getUrl() + "/.*"))
//                .collect(Collectors.toList());
//    }
}
