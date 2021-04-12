package idv.rennnhong.backendstarterkit.controller;

import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.CreatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.UpdatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.service.ActionService;
import idv.rennnhong.backendstarterkit.service.PermissionService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.response.ErrorMessages;
import idv.rennnhong.common.response.ResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
@Api(tags = {"頁面資料"})
@SwaggerDefinition(tags = {
        @Tag(name = "頁面資料", description = "頁面管理API文件")
})
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    ActionService actionService;

    @GetMapping
    @ApiOperation("取得所有頁面資料")
    public ResponseEntity getPermissions(@RequestParam(defaultValue = "1") Integer pageNumber,
                                         @RequestParam(defaultValue = "10") Integer rowsPerPage) {
        PageableResult<PermissionDto> result = permissionService.pageAll(pageNumber, rowsPerPage);
        return new ResponseEntity(ResponseBody.newPageableBody(result), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @ApiOperation("取得所有頁面資料")
    public ResponseEntity getPermission(@PathVariable UUID id) {
        PermissionDto permission = permissionService.getById(id);
        return new ResponseEntity(ResponseBody.newSingleBody(permission), HttpStatus.OK);
    }


    @GetMapping("/{permissionId}/actions")
    @ApiOperation("取得頁面所有功能資料")
    public ResponseEntity<?> getActionsOfPermission(@PathVariable String permissionId) {
        Collection<ActionDto> actions = actionService.getActionsByPermissionId(permissionId);
        ResponseBody<Collection<ActionDto>> responseBody = ResponseBody.newCollectionBody(actions);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping
    @ApiOperation("新增頁面資料")
    public ResponseEntity<?> createPermission(@RequestBody CreatePermissionRequestDto createPermissionRequestDto) {
        PermissionDto permission = permissionService.save(createPermissionRequestDto);
        ResponseBody<PermissionDto> responseBody = ResponseBody.newSingleBody(permission);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @ApiOperation("修改頁面資料")
    public ResponseEntity updatePermission(@PathVariable UUID id,
                                           @RequestBody UpdatePermissionRequestDto updatePermissionRequestDto) {
        PermissionDto updatedPermission = permissionService.update(id, updatePermissionRequestDto);
//        if (ObjectUtils.isEmpty(updatedPermission)) {
//            return new ResponseEntity<Object>(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
//        }
        ResponseBody<PermissionDto> responseBody = ResponseBody.newSingleBody(updatedPermission);
        return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @ApiOperation("刪除頁面資料")
    public ResponseEntity<?> deletePermission(@PathVariable UUID id) {
        if (!permissionService.isExist(id)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pageId}/actions")
    @ApiOperation("新增頁面功能資料")
    public ResponseEntity createAction(@PathVariable UUID pageId,
                                       @RequestBody CreateActionRequestDto createActionRequestDto) {
        if (!permissionService.isExist(pageId)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
        ActionDto savedAction = actionService.save(createActionRequestDto);
        ResponseBody<ActionDto> responseBody = ResponseBody.newSingleBody(savedAction);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }


    @PutMapping("/{permissionId}/actions/{actionId}")
    @ApiOperation("修改頁面功能資料")
    public ResponseEntity updateAction(@PathVariable UUID permissionId,
                                       @PathVariable UUID actionId,
                                       @RequestBody UpdateActionRequestDto updateActionRequestDto) {

        if (!actionService.isExist(actionId)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
        ActionDto updatedAction = actionService.update(permissionId, actionId, updateActionRequestDto);
        ResponseBody<ActionDto> responseBody = ResponseBody.newSingleBody(updatedAction);
        return ResponseEntity.ok(responseBody);
    }


    @DeleteMapping("/actions/{actionId}")
    @ApiOperation("刪除頁面功能資料")
    public ResponseEntity<?> deleteAction(@PathVariable UUID actionId, @PathVariable UUID pageId) {
        if (!actionService.isExist(actionId)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
        actionService.delete(actionId);
        return ResponseEntity.noContent().build();
    }


}
