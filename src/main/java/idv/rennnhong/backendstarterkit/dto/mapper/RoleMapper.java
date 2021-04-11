package idv.rennnhong.backendstarterkit.dto.mapper;

import idv.rennnhong.backendstarterkit.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface RoleMapper {

    Role createEntity(CreateRoleRequestDto dto);

    void updateEntity(@MappingTarget Role role, UpdateRoleRequestDto dto);

    RoleDto toDto(Role entity);

    Collection<RoleDto> toDto(Collection<Role> entities);

}
