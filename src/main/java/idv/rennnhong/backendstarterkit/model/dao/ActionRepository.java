package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.backendstarterkit.model.entity.Action;
import idv.rennnhong.common.BaseRepository;
import idv.rennnhong.backendstarterkit.model.entity.Permission;

import java.util.List;
import java.util.UUID;

public interface ActionRepository extends BaseRepository<Action, UUID> {

    List<Action> findAllByPermission(Permission permission);

}
