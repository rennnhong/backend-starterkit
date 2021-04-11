package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableSet;
import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.dto.mapper.ActionMapper;
import idv.rennnhong.backendstarterkit.dto.mapper.PermissionMapper;
import idv.rennnhong.backendstarterkit.model.dao.ActionDao;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.entity.Action;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.service.ActionService;
import idv.rennnhong.common.query.PageableResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class ActionServiceImpl implements ActionService {

    final ActionDao actionDao;

    final ActionMapper actionMapper;

    PermissionMapper permissionMapper;

    PermissionDao permissionDao;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Autowired
    public void setPermissionDao(PermissionDao permissionDao) {
        this.permissionDao = permissionDao;
    }

    @Autowired
    public ActionServiceImpl(ActionDao actionDao, ActionMapper actionMapper) {
        this.actionDao = actionDao;
        this.actionMapper = actionMapper;
    }

    @Override
    public Collection<ActionDto> getAll(UUID permissionId) {
        Permission permission = permissionDao.findById(permissionId).get();
        List<Action> actions = actionDao.findAllByPermission(permission);
        return actionMapper.toDto(actions);
    }

    @Override
    public ActionDto getById(UUID id) {
        Action action = actionDao.findById(id).get();
        return actionMapper.toDto(action);
    }

    @Override
    public ActionDto save(CreateActionRequestDto createActionRequestDto) {
        Action action = actionMapper.createEntity(createActionRequestDto);
        Action savedAction = actionDao.save(action);
        return actionMapper.toDto(savedAction);
    }

    @Override
    public ActionDto update(UUID id, UpdateActionRequestDto updateActionRequestDto) {
        Action action = actionDao.findById(id).get();
        actionMapper.updateEntity(action, updateActionRequestDto);
        Action updatedAction = actionDao.save(action);
        return actionMapper.toDto(updatedAction);
    }

    @Override
    public void delete(UUID id) {
        actionDao.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return actionDao.existsById(id);
    }

    @Override
    public PageableResult<ActionDto> pageAllByPermission(Permission permission, Integer pageNumber,
                                                         Integer rowsPerPage) {
        return null;
    }

    @Override
    public Set<ActionDto> getActionsByPermissionId(String permissionId) {
        Permission permission = permissionDao.findById(UUID.fromString(permissionId)).get();
        return ImmutableSet.copyOf(actionMapper.toDto(permission.getActions()));
    }

//    @Override
//    public ActionDto save(String permissionId, ActionDto actionDto) {
//        Permission permission = permissionDao.findById(UUID.fromString(permissionId)).get();
//        Action action = actionMapper.toEntity(actionDto);
//        permission.getActions().add(action);
//        permissionDao.save(permission);
//        return actionMapper.toDto(action);
//    }
//
//    @Override
//    public ActionDto update(String actionId, ActionDto actionDto) {
//        Action action = actionDao.findById(UUID.fromString(actionId)).get();
//        actionMapper.populateDto(action,actionDto);
//
//    }
}
