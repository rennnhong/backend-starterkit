package idv.rennnhong.backendstarterkit.controller.request.role;

import lombok.Data;

import java.util.UUID;


@Data
public class CreateRoleRequestDto {

    String name;

    String code;
}
