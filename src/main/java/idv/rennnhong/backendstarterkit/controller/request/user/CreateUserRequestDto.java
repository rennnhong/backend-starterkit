package idv.rennnhong.backendstarterkit.controller.request.user;

import lombok.Data;

@Data
public class CreateUserRequestDto {

    private String id;

    private String userName;

    private String account;

    private String password;

    private String[] roleIds;

}
