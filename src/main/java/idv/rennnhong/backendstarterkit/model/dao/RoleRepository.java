package idv.rennnhong.backendstarterkit.model.dao;

import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.common.SoftDeleteRepository;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends SoftDeleteRepository<Role, UUID> {

    List<Role> findAllByIdIn(List<UUID> ids);
}
