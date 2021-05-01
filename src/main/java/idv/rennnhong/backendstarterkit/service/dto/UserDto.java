package idv.rennnhong.backendstarterkit.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    private String userName;

    private String password;

    private String account;

    private LocalDate birthday;

    private String gender;

    private String email;

    private String phone;

    private String city;

    private List<String> roles;

}

