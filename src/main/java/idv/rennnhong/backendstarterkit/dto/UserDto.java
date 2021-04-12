package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    private String userName;

    private String account;

    private Date birthday;

    private String gender;

    private String email;

    private String phone;

    private String city;

    private List<String> roles;

}

