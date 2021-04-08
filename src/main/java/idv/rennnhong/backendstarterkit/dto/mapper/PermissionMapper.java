package idv.rennnhong.backendstarterkit.dto.mapper;

import idv.rennnhong.common.BaseMapper;
import idv.rennnhong.backendstarterkit.controller.request.permission.CreatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.UpdatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    imports = UUID.class
)
public interface PermissionMapper extends BaseMapper<PermissionDto, Permission> {

    void populateDto(@MappingTarget PermissionDto roleDto, CreatePermissionRequestDto createPermissionRequestDto);

    void populateDto(@MappingTarget PermissionDto roleDto, UpdatePermissionRequestDto updatePermissionRequestDto);
}
