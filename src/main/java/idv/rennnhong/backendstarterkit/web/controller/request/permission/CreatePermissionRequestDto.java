package idv.rennnhong.backendstarterkit.web.controller.request.permission;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.UUID;


@Data
public class CreatePermissionRequestDto {

    @NotEmpty
    String name;

    String sorted;

    String route;

    String opened;

    String icon;

    UUID parentId;
}
