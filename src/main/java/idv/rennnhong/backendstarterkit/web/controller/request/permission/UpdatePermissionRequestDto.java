package idv.rennnhong.backendstarterkit.web.controller.request.permission;

import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class UpdatePermissionRequestDto {

    @NotEmpty
    String name;

    String sorted;

    String route;

    String opened;

    String icon;
}
