package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto{

    private UUID id;

    private String name;

    private String code;

}
