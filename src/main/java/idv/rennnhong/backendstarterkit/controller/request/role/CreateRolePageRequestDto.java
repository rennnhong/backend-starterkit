package idv.rennnhong.backendstarterkit.controller.request.role;

import javax.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class CreateRolePageRequestDto {

    @NotEmpty
    String pageId;

    @NotEmpty
    String roleId;

    String[] actionId;
}
