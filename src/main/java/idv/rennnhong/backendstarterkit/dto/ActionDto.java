package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ActionDto {

    private UUID id;

    private String name;

    private String icon;

    private Integer sorted;

    private Integer disabled;

    private String functionName;

    private String apiId;

    private String pageId;
}
