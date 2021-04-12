package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PermissionDto {

    private String name;


    private String icon;


    private String route;


    private Integer sorted;


    private Integer opened;


    private UUID parentId;


}
