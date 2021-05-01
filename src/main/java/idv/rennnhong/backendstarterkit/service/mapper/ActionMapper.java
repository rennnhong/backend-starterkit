package idv.rennnhong.backendstarterkit.service.mapper;

import idv.rennnhong.backendstarterkit.service.dto.ActionEditDto;
import idv.rennnhong.backendstarterkit.service.dto.ActionDto;
import idv.rennnhong.backendstarterkit.entity.Action;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface ActionMapper {

    @Mapping(target = "id", ignore = true)
    Action createEntity(ActionDto dto);

    void updateEntity(@MappingTarget Action entity, ActionEditDto actionEditDto);

    ActionDto toDto(Action entity);

    Collection<ActionDto> toDto(Collection<Action> entities);
}
