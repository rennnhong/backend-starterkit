package idv.rennnhong.backendstarterkit.dto.mapper;

import com.google.common.collect.ImmutableList;
import idv.rennnhong.backendstarterkit.web.controller.request.user.CreateUserRequestDto;
import idv.rennnhong.backendstarterkit.web.controller.request.user.UpdateUserRequestDto;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.dto.UserPermissionDto;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.backendstarterkit.model.entity.UserPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User createEntity(CreateUserRequestDto dto);

    @Mapping(target = "roles", ignore = true)
    void updateEntity(@MappingTarget User entity, UpdateUserRequestDto dto);

    UserDto toDto(User entity);

    Collection<UserDto> toDto(Collection<User> entities);

    default List<String> rolesToRoleIds(Set<Role> roles) {
        if (ObjectUtils.isEmpty(roles)) return ImmutableList.of();
        return roles.stream().map(role -> role.getId().toString()).collect(Collectors.toList());

    }

}
