package idv.rennnhong.backendstarterkit.controller.request.role;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateRoleRequestDto {

    String name;

    String code;

    List<RolePermissionDto> rolePermissions;
}
