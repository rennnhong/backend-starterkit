package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import idv.rennnhong.common.BaseServiceImpl;
import idv.rennnhong.backendstarterkit.model.dao.ApiDao;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.entity.Api;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.RolePermission;
import idv.rennnhong.backendstarterkit.service.ApiService;
import idv.rennnhong.backendstarterkit.dto.ApiDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.mapper.ApiMapper;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiServiceImpl extends BaseServiceImpl<ApiDto, Api, UUID> implements ApiService {


    final ApiDao apiDao;


    final ApiMapper apiMapper;

    RoleDao roleDao;

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Autowired
    public ApiServiceImpl(ApiDao apiDao, ApiMapper apiMapper) {
        super(apiDao, apiMapper);
        this.apiDao = (ApiDao) super.baseDao;
        this.apiMapper = apiMapper;
    }

    @Override
    public List<ApiDto> getAllApiByRole(RoleDto role, String url, String httpMethod) {
        Role roleEntity = roleDao.findById(UUID.fromString(role.getId())).get();
        Set<RolePermission> rolePermissions = roleEntity.getRolePermissions();

//        List<Api> apiList = rolePermissions.stream()
//            .map(rolePermission -> rolePermission.getAction().getApi())
//            .filter(item -> url.matches(item.getUrl() + "/.*"))
//            .collect(Collectors.toList());
        return ImmutableList.copyOf(apiMapper.toDto(getApiList(rolePermissions, url)));
    }

    @Override
    public List<ApiDto> getAllApiByRoles(Set<RoleDto> roles, String url, String httpMethod) {
        List<UUID> roleIds = roles.stream()
            .map(roleDTO -> UUID.fromString(roleDTO.getId()))
            .collect(Collectors.toList());

        List<Role> roleEntities = roleDao.findAllByIdIn(roleIds);

        List<Set<RolePermission>> collect = roleEntities.stream().map(roleEntity -> roleEntity.getRolePermissions())
            .collect(Collectors.toList());

        List<Api> roleApiList = collect.stream()
            .map(rolePermissions -> getApiList(rolePermissions, url))
            .reduce((resultList, list) -> {
                resultList.addAll(list);
                return resultList;
            }).get();

//        List<List<ApiDTO>> roleApiList = roles
//            .stream()
//            .map(role -> getAllApiByRole(role, url, httpMethod))
//            .collect(Collectors.toList());

//        roleApiList
//            .stream()
//            .forEach(apis -> apiList.addAll(apis));
        return ImmutableList.copyOf(apiMapper.toDto(roleApiList));
    }

    private List<Api> getApiList(Set<RolePermission> rolePermissions, String url) {
        return rolePermissions.stream()
            .map(rolePermission -> rolePermission.getAction().getApi())
            .filter(item -> url.matches(item.getUrl() + "/.*"))
            .collect(Collectors.toList());
    }
}
