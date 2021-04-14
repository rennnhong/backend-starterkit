package idv.rennnhong.backendstarterkit.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class UserDto {

    private UUID id;

    private String userName;

    private String account;

    private LocalDate birthday;

    private String gender;

    private String email;

    private String phone;

    private String city;

    private List<String> roles;

}

