package idv.rennnhong.backendstarterkit.controller;

import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.response.ErrorMessages;
import idv.rennnhong.common.response.ResponseBody;
import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.CreatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.permission.UpdatePermissionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.dto.PermissionDto;
import idv.rennnhong.backendstarterkit.dto.mapper.ActionMapper;
import idv.rennnhong.backendstarterkit.dto.mapper.PermissionMapper;
import idv.rennnhong.backendstarterkit.service.ActionService;
import idv.rennnhong.backendstarterkit.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

import java.util.Collection;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/pages")
@Api(tags = {"頁面資料"})
@SwaggerDefinition(tags = {
        @Tag(name = "頁面資料", description = "頁面管理API文件")
})
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @Autowired
    ActionService actionService;

    @Autowired
    PermissionMapper permissionMapper;

    @Autowired
    ActionMapper actionMapper;

    @PostMapping
    @ApiOperation("新增頁面資料")
    public ResponseEntity<?> createPermission(@RequestBody CreatePermissionRequestDto createPermissionRequestDto) {
        PermissionDto permissionDto = new PermissionDto();
//        permissionMapper.populateDto(permissionDto, createPermissionRequestDto);

        PermissionDto permission = permissionService.save(createPermissionRequestDto);
        ResponseBody<PermissionDto> responseBody = ResponseBody.newSingleBody(permission);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    @ApiOperation("修改頁面資料")
    public ResponseEntity updatePermission(@PathVariable UUID id,
                                           @RequestBody UpdatePermissionRequestDto updatePermissionRequestDto) {
//        PermissionDto permission = permissionService.getById(UUID.fromString(id));
//        permissionMapper.populateDto(permission, updatePermissionRequestDto);
        PermissionDto updatedPermission = permissionService.update(id, updatePermissionRequestDto);
        if (ObjectUtils.isEmpty(updatedPermission)) {
            return new ResponseEntity<Object>(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
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

    @GetMapping
    @ApiOperation("取得所有頁面資料")
    public ResponseEntity getPermissions(@RequestParam(defaultValue = "1") Integer pageNumber,
                                         @RequestParam(defaultValue = "10") Integer rowsPerPage) {

        PageableResult<PermissionDto> result = permissionService.pageAll(pageNumber, rowsPerPage);
        return new ResponseEntity(ResponseBody.newPageableBody(result), HttpStatus.OK);
    }

    @GetMapping("/{pageId}/actions")
    @ApiOperation("取得頁面所有功能資料")
    public ResponseEntity<?> getActionsOfPermission(@PathVariable String permissionId) {
        Collection<ActionDto> actions = actionService.getActionsByPermissionId(permissionId);
        ResponseBody<Collection<ActionDto>> responseBody = ResponseBody.newCollectionBody(actions);
        return ResponseEntity.ok(responseBody);
    }


    @PostMapping("/{pageId}/actions")
    @ApiOperation("新增頁面功能資料")
    public ResponseEntity createAction(@PathVariable UUID pageId,
                                       @RequestBody CreateActionRequestDto createActionRequestDto) {
        if (!permissionService.isExist(pageId)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
//        ActionDto actionDto = new ActionDto();
//        actionMapper.populateDto(actionDto, createActionRequestDto);
        ActionDto savedAction = actionService.save(createActionRequestDto);
        ResponseBody<ActionDto> responseBody = ResponseBody.newSingleBody(savedAction);
        return new ResponseEntity(responseBody, HttpStatus.CREATED);
    }


    @PutMapping("/{pageId}/actions/{actionId}")
    @ApiOperation("修改頁面功能資料")
    public ResponseEntity updateAction(@PathVariable UUID actionId,
                                       @PathVariable UUID pageId,
                                       @RequestBody UpdateActionRequestDto updateActionRequestDto) {

        if (!actionService.isExist(actionId)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }

//        ActionDto action = actionService.getById(UUID.fromString(actionId));
//        actionMapper.populateDto(action, updateActionRequestDto);
        ActionDto updatedAction = actionService.update(actionId, updateActionRequestDto);
        ResponseBody<ActionDto> responseBody = ResponseBody.newSingleBody(updatedAction);
        return ResponseEntity.ok(responseBody);
    }


    @DeleteMapping("/{pageId}/actions/{actionId}")
    @ApiOperation("刪除頁面功能資料")
    public ResponseEntity<?> deleteAction(@PathVariable UUID actionId, @PathVariable UUID pageId) {
        if (!actionService.isExist(actionId)) {
            return new ResponseEntity(ErrorMessages.RESOURCE_NOT_FOUND.toObject(), HttpStatus.NOT_FOUND);
        }
        actionService.delete(pageId);
        return ResponseEntity.noContent().build();
    }


}
