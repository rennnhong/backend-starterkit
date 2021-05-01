package idv.rennnhong.backendstarterkit.service.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserEditDto {

    private String userName;

    private String account;

    private LocalDate birthday;

    private String gender;

    private String email;

    private String phone;

    private String city;

    private List<String> roleIds;

}
