package idv.rennnhong.backendstarterkit.service.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ApiDto{

    private UUID id;

    private String url;

    private String httpMethod;
}
