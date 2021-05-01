package idv.rennnhong.backendstarterkit.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PermissionDto {

    private UUID id;

    private String name;


    private String icon;


    private String route;


    private Integer sorted;


    private Integer opened;


    private UUID parentId;


}
