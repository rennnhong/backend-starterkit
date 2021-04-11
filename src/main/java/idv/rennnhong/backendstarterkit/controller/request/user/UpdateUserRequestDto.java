package idv.rennnhong.backendstarterkit.controller.request.user;

import idv.rennnhong.backendstarterkit.controller.request.role.RolePermissionDto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UpdateUserRequestDto {

    private String userName;

    private String account;

    private String password;

    private Date birthday;

    private String gender;

    private String email;

    private String phone;

    private String city;

    private List<String> roleIds;

}
