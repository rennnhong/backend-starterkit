package idv.rennnhong.backendstarterkit.controller.request.role;

import idv.rennnhong.backendstarterkit.model.entity.Action;
import lombok.Data;


@Data
public class RolePageActionAllResponseDto {

    String id;

    String masterId;

    String name;

    Integer sorted;

    String route;

    String icon;

    Integer opened;

    Action action;

}
