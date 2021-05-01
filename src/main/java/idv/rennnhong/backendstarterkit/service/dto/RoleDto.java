package idv.rennnhong.backendstarterkit.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto{

    private UUID id;

    private String name;

    private String code;

}
