package idv.rennnhong.backendstarterkit.repository;

import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.common.SoftDeleteRepository;

import java.util.Collection;
import java.util.UUID;

public interface UserRepository extends SoftDeleteRepository<User, UUID> {

    User findByAccount(String account);

    boolean existsByAccount(String account);

    Collection<User> findAllByRolesIsIn(Collection<Role> role);

}
