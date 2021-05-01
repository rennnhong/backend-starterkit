package idv.rennnhong.backendstarterkit.service.dto;

import lombok.Data;

@Data
public class UserPermissionDto {

    private PermissionDto permission;

    private ActionDto action;

}
