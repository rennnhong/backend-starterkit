package idv.rennnhong.backendstarterkit.controller;

import static idv.rennnhong.common.response.ErrorMessages.RESOURCE_NOT_FOUND;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.response.ResponseBody;
import idv.rennnhong.backendstarterkit.controller.request.role.CreateRoleRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.UpdateRoleRequestDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.dto.mapper.RoleMapper;
import idv.rennnhong.backendstarterkit.service.PermissionService;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    RoleMapper roleMapper;

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

    @GetMapping("/{roleId}/users")
    @ApiOperation("取得指定角色的所有使用者資料")
    public ResponseEntity getUsersOfRole(@PathVariable UUID roleId) {
        Collection<UserDto> usersOfRole = userService.getUsersOfRole(roleId);
        ResponseBody<Collection<UserDto>> responseBody = ResponseBody.newCollectionBody(usersOfRole);
        return new ResponseEntity(responseBody, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ApiOperation("取得角色資料ById")
    public ResponseEntity getRoleById(@PathVariable UUID id) {
        RoleDto role = roleService.getById(id);
        ResponseBody<RoleDto> responseBody = ResponseBody.newSingleBody(role);
        return ResponseEntity.ok(responseBody);
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
        //todo 若無法刪除，應顯示還有哪些User在使用
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/auths")
    @ApiOperation("取得所有頁面權限角色資料")
    public ResponseEntity getPermissionsOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        if (!userService.isExist(UUID.fromString(userId))) {
            return new ResponseEntity(ResponseBody.newErrorMessageBody(RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        Set<RoleDto> roles = roleService.getRolesByUserId(UUID.fromString(userId));
        Collection<PermissionDto> permissions = permissionService.getPermissionsByRoles(roles);
        ResponseBody<Collection<PermissionDto>> responseBody = ResponseBody.newCollectionBody(permissions);
        return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
    }

}
