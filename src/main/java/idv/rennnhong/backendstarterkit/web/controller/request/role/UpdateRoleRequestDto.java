package idv.rennnhong.backendstarterkit.web.controller.request.role;

import lombok.Data;

import java.util.List;

@Data
public class UpdateRoleRequestDto {

    String name;

    String code;

    List<RolePermissionDto> rolePermissions;
}
