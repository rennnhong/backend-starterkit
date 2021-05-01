package idv.rennnhong.backendstarterkit.repository;

import idv.rennnhong.backendstarterkit.entity.Role;
import idv.rennnhong.common.SoftDeleteRepository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository extends SoftDeleteRepository<Role, UUID> {

    List<Role> findAllByIdIn(List<UUID> ids);

    Set<Role> findAllByCodeIn(Set<String> code);
}
