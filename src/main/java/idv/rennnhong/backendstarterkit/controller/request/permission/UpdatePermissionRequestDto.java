package idv.rennnhong.backendstarterkit.controller.request.permission;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

import java.util.UUID;


@Data
public class UpdatePermissionRequestDto {

    @NotEmpty
    String name;

    String sorted;

    String route;

    String opened;

    String icon;
}
