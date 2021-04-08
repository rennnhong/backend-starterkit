package idv.rennnhong.backendstarterkit.dto.mapper;

import idv.rennnhong.common.BaseMapper;
import idv.rennnhong.backendstarterkit.controller.request.user.CreateUserRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.user.UpdateUserRequestDto;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface UserMapper extends BaseMapper<UserDto, User> {

    void populateDto(@MappingTarget UserDto roleDto, CreateUserRequestDto createRoleRequestDto);

    void populateDto(@MappingTarget UserDto roleDto, UpdateUserRequestDto updateRoleRequestDto);
}
