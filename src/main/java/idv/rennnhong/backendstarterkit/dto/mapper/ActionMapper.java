package idv.rennnhong.backendstarterkit.dto.mapper;


import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.model.entity.Action;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface ActionMapper {

    Action createEntity(CreateActionRequestDto dto);

    void updateEntity(@MappingTarget Action entity, UpdateActionRequestDto updateActionRequestDto);

    ActionDto toDto(Action entity);

    Collection<ActionDto> toDto(Collection<Action> entities);
}
