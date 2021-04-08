package idv.rennnhong.backendstarterkit.dto.mapper;


import idv.rennnhong.common.BaseMapper;
import idv.rennnhong.backendstarterkit.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.model.entity.Api;
import org.mapstruct.Mapper;
import idv.rennnhong.backendstarterkit.dto.ApiDto;
import org.mapstruct.MappingTarget;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    imports = UUID.class
)
public interface ApiMapper extends BaseMapper<ApiDto, Api> {

    void populateDto(@MappingTarget RoleDto roleDto, CreateRoleRequestDto createRoleRequestDto);

    void populateDto(@MappingTarget RoleDto roleDto, UpdateRoleRequestDto updateRoleRequestDto);
}
