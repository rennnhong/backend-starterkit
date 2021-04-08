package idv.rennnhong.backendstarterkit.controller.request.user;

import javax.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateUserRequestDto {

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
