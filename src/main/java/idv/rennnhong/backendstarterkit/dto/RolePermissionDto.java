package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;


@Data
public class RolePermissionDto {

    private String roleId;
    private String permissionId;
    private String actionId;

}
