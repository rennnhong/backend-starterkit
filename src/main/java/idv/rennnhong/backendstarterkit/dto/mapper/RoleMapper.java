package idv.rennnhong.backendstarterkit.dto.mapper;

import idv.rennnhong.common.BaseMapper;
import idv.rennnhong.backendstarterkit.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    imports = UUID.class
)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {

//    RoleDto toDto(CreateRoleRequestDto createRoleRequestDto);
//
//    RoleDto toDto(UpdateRoleRequestDto updateRoleRequestDto);

    void populateDto(@MappingTarget RoleDto roleDto, CreateRoleRequestDto createRoleRequestDto);

    void populateDto(@MappingTarget RoleDto roleDto, UpdateRoleRequestDto updateRoleRequestDto);

}
