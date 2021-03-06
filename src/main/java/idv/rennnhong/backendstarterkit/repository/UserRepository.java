package idv.rennnhong.backendstarterkit.repository;

import idv.rennnhong.backendstarterkit.entity.Role;
import idv.rennnhong.backendstarterkit.entity.User;
import idv.rennnhong.common.SoftDeleteRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends SoftDeleteRepository<User, UUID> {

    Optional<User> findByAccount(String account);

    boolean existsByAccount(String account);

    Collection<User> findAllByRolesIsIn(Collection<Role> role);

}
