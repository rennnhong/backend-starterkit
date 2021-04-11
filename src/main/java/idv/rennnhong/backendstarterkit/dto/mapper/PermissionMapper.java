package idv.rennnhong.backendstarterkit.dto.mapper;

import idv.rennnhong.backendstarterkit.controller.request.permission.CreatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.UpdatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface PermissionMapper {

    Permission createEntity(CreatePermissionRequestDto dto);

    void updateEntity(@MappingTarget Permission entity, UpdatePermissionRequestDto dto);

    PermissionDto toDto(Permission entity);

    Collection<PermissionDto> toDto(Collection<Permission> entities);
}
