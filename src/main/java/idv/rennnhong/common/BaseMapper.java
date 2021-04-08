/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package idv.rennnhong.common;

import org.mapstruct.Mapping;

import java.util.Collection;

public interface BaseMapper<D, E> {

    /**
     * DTO转Entity
     *
     * @param dto /
     * @return /
     */
    @Mapping(target = "id", expression = "java( dto.getId() != null? UUID.fromString(dto.getId()) : null )")
    E toEntity(D dto);

    /**
     * Entity转DTO
     *
     * @param entity /
     * @return /
     */
    @Mapping(target = "id", expression = "java( entity.getId().toString() )")
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     *
     * @param dtoList /
     * @return /
     */
    Collection<E> toEntity(Collection<D> dtos);

    /**
     * Entity集合转DTO集合
     *
     * @param entityList /
     * @return /
     */
    Collection<D> toDto(Collection<E> entities);


}
