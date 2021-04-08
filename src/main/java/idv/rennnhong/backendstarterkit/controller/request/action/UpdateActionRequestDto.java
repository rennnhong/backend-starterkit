package idv.rennnhong.backendstarterkit.controller.request.action;

import lombok.Data;
import javax.validation.constraints.NotEmpty;

@Data
public class UpdateActionRequestDto {

    @NotEmpty
    String name;

    String icon;

    Integer sorted;

    Integer disabled;

    String functionName;
}
