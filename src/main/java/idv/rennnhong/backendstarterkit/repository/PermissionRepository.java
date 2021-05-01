package idv.rennnhong.backendstarterkit.repository;

import idv.rennnhong.backendstarterkit.entity.Permission;
import idv.rennnhong.common.BaseRepository;

import java.util.UUID;

public interface PermissionRepository extends BaseRepository<Permission, UUID> {

    boolean existsByParentId(UUID id);

}
