package idv.rennnhong.backendstarterkit.dto.mapper;


import idv.rennnhong.backendstarterkit.controller.request.api.CreateApiRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.api.UpdateApiRequestDto;
import idv.rennnhong.backendstarterkit.dto.ApiDto;
import idv.rennnhong.backendstarterkit.model.entity.Action;
import idv.rennnhong.backendstarterkit.model.entity.Api;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.UUID;

@Mapper(
        componentModel = "spring",
        imports = UUID.class
)
public interface ApiMapper {

    Api createEntity(CreateApiRequestDto dto);

    void updateEntity(@MappingTarget Api Api, UpdateApiRequestDto updateActionRequestDto);

    ApiDto toDto(Api entity);

    Collection<ApiDto> toDto(Collection<Api> entities);
}
