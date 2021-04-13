package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.common.BaseRepository;

import java.util.List;
import java.util.UUID;

public interface PermissionRepository extends BaseRepository<Permission, UUID> {

    boolean existsByParentId(UUID id);

}
