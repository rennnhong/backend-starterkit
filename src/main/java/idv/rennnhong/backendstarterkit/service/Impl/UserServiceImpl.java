package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import idv.rennnhong.backendstarterkit.controller.request.user.CreateUserRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.user.UpdateUserRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.RolePermissionDto;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.dto.mapper.UserMapper;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.dao.UserDao;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.query.PageableResultImpl;
import idv.rennnhong.common.query.QueryParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
class UserServiceImpl implements UserService {

    final UserDao userDao;

    final UserMapper userMapper;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto getUserByAccount(String account) {
        return userMapper.toDto(userDao.findByAccount(account));
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userDao.findAll();
        return Lists.newArrayList(userMapper.toDto(users));
    }

    @Override
    public UserDto getById(UUID uuid) {
        User user = userDao.findById(uuid).get();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto save(CreateUserRequestDto dto) {
        User user = userMapper.createEntity(dto);

        //處理Roles
        if (!ObjectUtils.isEmpty(dto.getRoleIds())) setUserRoles(user, dto.getRoleIds());

        User savedUser = userDao.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto update(UUID id, UpdateUserRequestDto dto) {
        User user = userDao.findById(id).get();
        userMapper.updateEntity(user, dto);

        //處理Roles
        user.getRoles().clear();
        if (!ObjectUtils.isEmpty(dto.getRoleIds())) setUserRoles(user, dto.getRoleIds());

        User updatedUser = userDao.save(user);
        return userMapper.toDto(updatedUser);
    }

    private void setUserRoles(User user, List<String> roleIds) {
        List<UUID> uuidList = roleIds.stream()
                .map(roleId -> UUID.fromString(roleId))
                .collect(Collectors.toList());
        List<Role> roles = roleDao.findAllByIdIn(uuidList);
        user.getRoles().addAll(roles);
    }

    @Override
    public void delete(UUID id) {
        userDao.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return userDao.existsById(id);
    }

//    @Override
//    public UserDto save(UserDto userDto) {
//        List<UUID> uuidList = userDto.getRoles().stream()
//                .map(roleId -> UUID.fromString(roleId))
//                .collect(Collectors.toList());
//
//        List<Role> roles = roleDao.findAllByIdIn(uuidList);
//
//        User user = userMapper.toEntity(userDto);
//        user.setRoles(Sets.newHashSet(roles));
//        User savedUser = userDao.save(user);
//        return userMapper.toDto(savedUser);
//    }

    @Override
    public PageableResult<UserDto> pageAll(Integer pageNumber, Integer rowsPerPage) {
        QueryParameter qp = new QueryParameter()
                .addPageNumber(pageNumber)
                .addRowsPerPage(rowsPerPage)
                .build();

        Page<User> resultPage = userDao.findAll(
                PageRequest.of(qp.getPageOffset(), qp.getPageLimit()));

        List<UserDto> userDtos = ImmutableList.copyOf(userMapper.toDto(resultPage.getContent()));

        return new PageableResultImpl<UserDto>(
                qp.getPageLimit(),
                qp.getPageNumber(),
                resultPage.getTotalPages(),
                resultPage.getTotalElements(),
                userDtos);
    }

    @Override
    public boolean isExistByAccount(String userAccount) {
        return userDao.existsByAccount(userAccount);
    }

    @Override
    public Collection<UserDto> getUsersOfRole(UUID roleId) {
        Role role = roleDao.findById(roleId).get();
        List<Role> roles = Collections.singletonList(role);
        Collection<User> users = userDao.findAllByRolesIsIn(roles);
        return userMapper.toDto(users);
    }

}
