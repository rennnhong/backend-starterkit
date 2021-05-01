package idv.rennnhong.backendstarterkit.service.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;


@Data
public class PermissionEditDto {

    @NotEmpty
    String name;

    String sorted;

    String route;

    String opened;

    String icon;
}
