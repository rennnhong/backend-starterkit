package idv.rennnhong.backendstarterkit.controller.request.permission;

import javax.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class CreatePermissionRequestDto {
    @NotEmpty
    String masterId;

    @NotEmpty
    String name;

    String sorted;

    String route;

    String opened;

    String icon;
}