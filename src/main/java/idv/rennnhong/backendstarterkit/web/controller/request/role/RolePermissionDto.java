package idv.rennnhong.backendstarterkit.web.controller.request.role;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class RolePermissionDto {

    private UUID permissionId;
    private List<UUID> actionIds;
}
