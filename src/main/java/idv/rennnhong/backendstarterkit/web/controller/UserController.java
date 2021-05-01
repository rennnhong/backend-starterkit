package idv.rennnhong.backendstarterkit.web.controller;

import idv.rennnhong.backendstarterkit.service.dto.UserEditDto;
import idv.rennnhong.backendstarterkit.service.dto.UserDto;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.common.utils.BindingResultHelper;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.response.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

import static idv.rennnhong.common.response.ErrorMessages.INVALID_FIELDS_REQUEST;

@RestController
@RequestMapping("/api/users")
@Api(tags = {"使用者資料"})
@SwaggerDefinition(tags = {
        @Tag(name = "使用者資料", description = "使用者資料API文件")
})
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @ApiOperation("取得使用者資料")
    public ResponseEntity getUsers(@RequestParam(defaultValue = "1") int pageNumber,
                                   @RequestParam(defaultValue = "10") int rowsPerPage) {
        PageableResult<UserDto> result = userService.pageAll(pageNumber, rowsPerPage);
        ResponseBody<Collection<UserDto>> responseBody = ResponseBody.newPageableBody(result);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("查詢使用者資料 By userId")
    public ResponseEntity getUserById(@PathVariable UUID id) {
        return new ResponseEntity(userService.getById(id), HttpStatus.OK);
    }


    @GetMapping(params = {"account"})
    @ApiOperation("查詢使用者資料 By Account")
    public ResponseEntity getUserByAccount(@RequestParam("account") String account) {
        if ("".equals(account)) {
            return new ResponseEntity(ResponseBody.newErrorMessageBody(INVALID_FIELDS_REQUEST),
                    HttpStatus.BAD_REQUEST);
        }

        UserDto user = userService.getUserByAccount(account);
        ResponseBody<UserDto> responseBody = ResponseBody.newSingleBody(user);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("建立使用者資料")
    public ResponseEntity createUser(
            @Valid @RequestBody UserDto userDto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Object errorMap = BindingResultHelper.toHashMap(bindingResult);
            return new ResponseEntity(
                    ResponseBody.newErrorMessageBody(INVALID_FIELDS_REQUEST, errorMap),
                    HttpStatus.BAD_REQUEST);
        }

        UserDto savedUserDto = userService.save(userDto);
        return new ResponseEntity(ResponseBody.newSingleBody(savedUserDto), HttpStatus.OK);

    }

    @PutMapping("/{id}")
    @ApiOperation("更新使用者資料")
    public ResponseEntity updateUser(
            @PathVariable("id") UUID id,
            @Valid @RequestBody UserEditDto userEditDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Object errorMap = BindingResultHelper.toHashMap(bindingResult);
            return new ResponseEntity(
                    ResponseBody.newErrorMessageBody(INVALID_FIELDS_REQUEST, errorMap),
                    HttpStatus.BAD_REQUEST);
        }

        UserDto updatedUserDto = userService.update(id, userEditDto);
        return new ResponseEntity(updatedUserDto, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("刪除使用者資料")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if ("".equals(id)) {
            return new ResponseEntity(ResponseBody.newErrorMessageBody(INVALID_FIELDS_REQUEST),
                    HttpStatus.BAD_REQUEST);
        }
        userService.delete(UUID.fromString(id));
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
