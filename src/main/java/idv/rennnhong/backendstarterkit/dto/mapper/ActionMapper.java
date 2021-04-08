package idv.rennnhong.backendstarterkit.dto.mapper;


import idv.rennnhong.common.BaseMapper;
import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.model.entity.Action;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.UUID;

@Mapper(
    componentModel = "spring",
    imports = UUID.class
)
public interface ActionMapper extends BaseMapper<ActionDto, Action> {


    void populateDto(@MappingTarget ActionDto roleDto, CreateActionRequestDto createActionRequestDto);

    void populateDto(@MappingTarget ActionDto roleDto, UpdateActionRequestDto updateActionRequestDto);

}
