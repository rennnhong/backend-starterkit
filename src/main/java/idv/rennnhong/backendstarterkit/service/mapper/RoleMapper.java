package idv.rennnhong.backendstarterkit.service.mapper;

import idv.rennnhong.backendstarterkit.service.dto.RoleEditDto;
import idv.rennnhong.backendstarterkit.service.dto.RoleDto;
import idv.rennnhong.backendstarterkit.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    Role createEntity(RoleDto dto);

    void updateEntity(@MappingTarget Role role, RoleEditDto dto);

    RoleDto toDto(Role entity);

    Collection<RoleDto> toDto(Collection<Role> entities);

}
