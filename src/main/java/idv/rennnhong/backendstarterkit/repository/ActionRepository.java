package idv.rennnhong.backendstarterkit.repository;

import idv.rennnhong.backendstarterkit.entity.Action;
import idv.rennnhong.common.BaseRepository;
import idv.rennnhong.backendstarterkit.entity.Permission;

import java.util.List;
import java.util.UUID;

public interface ActionRepository extends BaseRepository<Action, UUID> {

    List<Action> findAllByPermission(Permission permission);

}
