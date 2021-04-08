package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableSet;
import idv.rennnhong.common.BaseServiceImpl;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.backendstarterkit.model.dao.ActionDao;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.entity.Action;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.service.ActionService;
import idv.rennnhong.backendstarterkit.dto.ActionDto;
import idv.rennnhong.backendstarterkit.dto.mapper.ActionMapper;

import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActionServiceImpl extends BaseServiceImpl<ActionDto, Action, UUID> implements ActionService {

    final ActionDao actionDao;

    final PermissionDao permissionDao;

    final ActionMapper actionMapper;


    @Autowired
    public ActionServiceImpl(ActionDao actionDao, PermissionDao permissionDao, ActionMapper actionMapper) {
        super(actionDao, actionMapper);
        this.actionDao = (ActionDao) super.baseDao;
        this.permissionDao = permissionDao;
        this.actionMapper = actionMapper;
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
