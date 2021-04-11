package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDto{
    
    private String userName;
    private String account;
//以下為商業邏輯==========================================

    private Date birthday;
    private String gender;
    private String email;
    private String phone;
    private String city;

    private List<String> roles;

}

