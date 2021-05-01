package idv.rennnhong.backendstarterkit.web.controller.request.action;

import javax.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateActionRequestDto {

    @NotEmpty
    String name;

    String icon;

    Integer sorted;

    Integer disabled;

    String functionName;
}
