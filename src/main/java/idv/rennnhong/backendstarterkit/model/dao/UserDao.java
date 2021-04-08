package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.common.BaseDao;
import idv.rennnhong.backendstarterkit.model.entity.User;
import java.util.UUID;

public interface UserDao extends BaseDao<User, UUID> {

    User findByAccount(String account);

    boolean existsByAccount(String account);

}
