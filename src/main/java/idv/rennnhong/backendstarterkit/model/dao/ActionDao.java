package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.backendstarterkit.model.entity.Action;
import idv.rennnhong.common.BaseDao;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import java.util.UUID;

public interface ActionDao extends BaseDao<Action, UUID> {

    Action findByPermission(Permission permission);

}
