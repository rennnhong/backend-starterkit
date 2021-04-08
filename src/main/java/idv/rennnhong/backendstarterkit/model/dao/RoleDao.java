package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.common.BaseDao;
import java.util.List;
import java.util.UUID;

public interface RoleDao extends BaseDao<Role, UUID> {

    List<Role> findAllByIdIn(List<UUID> ids);
}
