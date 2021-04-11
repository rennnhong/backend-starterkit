package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.common.BaseDao;

import java.util.List;
import java.util.UUID;

public interface PermissionDao extends BaseDao<Permission, UUID> {

    boolean existsByParentId(UUID id);

}
