package idv.rennnhong.backendstarterkit.service;

import idv.rennnhong.common.BaseService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.backendstarterkit.dto.UserDto;

import java.util.UUID;

public interface UserService extends BaseService<UserDto, UUID> {

//    UserDto saveUser(UserDto userDto);

    PageableResult<UserDto> pageAll(Integer pageNumber, Integer rowsPerPage);

    UserDto getUserByAccount(String account);


    boolean isLoginStatus(String account, String password);

    boolean changeUserPassword(String userId, String password);

    boolean isExistByAccount(String userAccount);


}
