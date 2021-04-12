package idv.rennnhong.backendstarterkit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.rennnhong.backendstarterkit.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.service.PermissionService;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.response.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import static idv.rennnhong.common.response.ErrorMessages.RESOURCE_NOT_FOUND;


@RestController
@RequestMapping("/api/roles")
@Api(tags = {"角色資料"})
@SwaggerDefinition(tags = {
        @Tag(name = "角色資料", description = "角色資料API文件")
})
public class RoleController {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    UserService userService;

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @ApiOperation("取得所有角色資料")
    public ResponseEntity getRoles(@RequestParam(defaultValue = "1") Integer pageNumber,
                                   @RequestParam(defaultValue = "10") Integer rowsPerPage) {
        PageableResult<RoleDto> roles = roleService.pageAll(pageNumber, rowsPerPage);
        ResponseBody<Collection<RoleDto>> responseBody = ResponseBody.newPageableBody(roles);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{id}")
    @ApiOperation("取得角色資料ById")
    public ResponseEntity getRoleById(@PathVariable UUID id) {
        RoleDto role = roleService.getById(id);
        ResponseBody<RoleDto> responseBody = ResponseBody.newSingleBody(role);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/{roleId}/users")
    @ApiOperation("取得指定角色的所有使用者資料")
    public ResponseEntity getUsersOfRole(@PathVariable UUID roleId) {
        Collection<UserDto> usersOfRole = userService.getUsersOfRole(roleId);
        ResponseBody<Collection<UserDto>> responseBody = ResponseBody.newCollectionBody(usersOfRole);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }


    @PostMapping
    @ApiOperation("新增角色資料")
    public ResponseEntity createRole(@RequestBody CreateRoleRequestDto createRoleRequestDto) {
        RoleDto savedRole = roleService.save(createRoleRequestDto);
        ResponseBody<RoleDto> responseBody = ResponseBody.newSingleBody(savedRole);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @ApiOperation("修改角色資料")
    public ResponseEntity updateRole(@PathVariable("id") UUID id,
                                     @RequestBody UpdateRoleRequestDto updateRoleRequestDto) {
        RoleDto roleDto = roleService.getById(id);
        if (ObjectUtils.isEmpty(roleDto)) {
            return new ResponseEntity(ResponseBody.newErrorMessageBody(RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        RoleDto updatedRole = roleService.update(id, updateRoleRequestDto);
        ResponseBody<RoleDto> responseBody = ResponseBody.newSingleBody(updatedRole);
        return ResponseEntity.ok(responseBody);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("刪除角色資料")
    public ResponseEntity deleteRole(@PathVariable UUID id) {
        if (!roleService.isExist(id)) {
            return new ResponseEntity(ResponseBody.newErrorMessageBody(RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        if (roleService.isRoleReferenced(id)) {
            //todo 若有使用者還在用該角色，拋出異常
            return new ResponseEntity(ResponseBody.newSingleBody("該角色目前還有其他使用者引用"), HttpStatus.BAD_REQUEST);
        }

        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }


//    @GetMapping("/auths")
//    @ApiOperation("取得所有頁面權限角色資料")
//    public ResponseEntity getPermissionsOfUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userId = authentication.getName();
//        if (!userService.isExist(UUID.fromString(userId))) {
//            return new ResponseEntity(ResponseBody.newErrorMessageBody(RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
//        }
//        Set<RoleDto> roles = roleService.getRolesByUserId(UUID.fromString(userId));
//        Collection<PermissionDto> permissions = permissionService.getPermissionsByRoles(roles);
//        ResponseBody<Collection<PermissionDto>> responseBody = ResponseBody.newCollectionBody(permissions);
//        return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
//    }


}
