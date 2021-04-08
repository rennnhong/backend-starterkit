package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

@Data
public class UserDto {


    private String id;
    private String userName;
    private String account;
//以下為商業邏輯==========================================

    private String birthday;
    private String gender;
    private String email;
    private String phone;
    private String city;


}

