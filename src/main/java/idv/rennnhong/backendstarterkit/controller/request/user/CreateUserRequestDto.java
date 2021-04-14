package idv.rennnhong.backendstarterkit.controller.request.user;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateUserRequestDto {

    private String userName;

    private String account;

    private String password;

    private LocalDate birthday;

    private String gender;

    private String email;

    private String phone;

    private String city;

    private List<String> roleIds;
}
