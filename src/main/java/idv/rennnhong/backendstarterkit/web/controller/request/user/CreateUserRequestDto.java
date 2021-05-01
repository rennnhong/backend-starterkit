package idv.rennnhong.backendstarterkit.web.controller.request.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
public class CreateUserRequestDto {

    @NotBlank
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
