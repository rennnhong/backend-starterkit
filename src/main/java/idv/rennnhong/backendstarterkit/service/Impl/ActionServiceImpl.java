package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableSet;
import idv.rennnhong.backendstarterkit.controller.request.action.CreateActionRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.action.UpdateActionRequestDto;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.dto.mapper.ActionMapper;
import idv.rennnhong.backendstarterkit.dto.mapper.PermissionMapper;
import idv.rennnhong.backendstarterkit.model.dao.ActionRepository;
import idv.rennnhong.backendstarterkit.model.dao.PermissionRepository;
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

    final ActionRepository actionRepository;

    final ActionMapper actionMapper;

    PermissionMapper permissionMapper;

    PermissionRepository permissionRepository;

    @Autowired
    public void setPermissionMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    @Autowired
    public void setPermissionRepository(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Autowired
    public ActionServiceImpl(ActionRepository actionRepository, ActionMapper actionMapper) {
        this.actionRepository = actionRepository;
        this.actionMapper = actionMapper;
    }

    @Override
    public Collection<ActionDto> getAll(UUID permissionId) {
        Permission permission = permissionRepository.findById(permissionId).get();
        List<Action> actions = actionRepository.findAllByPermission(permission);
        return actionMapper.toDto(actions);
    }

    @Override
    public ActionDto getById(UUID id) {
        Action action = actionRepository.findById(id).get();
        return actionMapper.toDto(action);
    }

    @Override
    public ActionDto save(CreateActionRequestDto createActionRequestDto) {
        Action action = actionMapper.createEntity(createActionRequestDto);
        Action savedAction = actionRepository.save(action);
        return actionMapper.toDto(savedAction);
    }

    @Override
    public ActionDto update(UUID id, UUID permissionId, UpdateActionRequestDto updateActionRequestDto) {
        Action action = actionRepository.findById(id).get();
        actionMapper.updateEntity(action, updateActionRequestDto);
        Action updatedAction = actionRepository.save(action);
        return actionMapper.toDto(updatedAction);
    }

    @Override
    public void delete(UUID id) {
        actionRepository.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return actionRepository.existsById(id);
    }

    @Override
    public PageableResult<ActionDto> pageAllByPermission(Permission permission, Integer pageNumber,
                                                         Integer rowsPerPage) {
        return null;
    }

    @Override
    public Set<ActionDto> getActionsByPermissionId(String permissionId) {
        Permission permission = permissionRepository.findById(UUID.fromString(permissionId)).get();
        return ImmutableSet.copyOf(actionMapper.toDto(permission.getActions()));
    }

//    @Override
//    public ActionDto save(String permissionId, ActionDto actionDto) {
//        Permission permission = permissionRepository.findById(UUID.fromString(permissionId)).get();
//        Action action = actionMapper.toEntity(actionDto);
//        permission.getActions().add(action);
//        permissionRepository.save(permission);
//        return actionMapper.toDto(action);
//    }
//
//    @Override
//    public ActionDto update(String actionId, ActionDto actionDto) {
//        Action action = actionRepository.findById(UUID.fromString(actionId)).get();
//        actionMapper.populateDto(action,actionDto);
//
//    }
}
