package idv.rennnhong.backendstarterkit.controller.request.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author wei-xiang
 * @version 1.0
 * @date 2020/10/15
 */
@Data
public class LoginRequestDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
