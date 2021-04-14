package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import idv.rennnhong.backendstarterkit.controller.request.user.CreateUserRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.user.UpdateUserRequestDto;
import idv.rennnhong.backendstarterkit.controller.request.role.RolePermissionDto;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.dto.mapper.UserMapper;
import idv.rennnhong.backendstarterkit.repository.PermissionRepository;
import idv.rennnhong.backendstarterkit.repository.RoleRepository;
import idv.rennnhong.backendstarterkit.repository.UserRepository;
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

    final UserRepository userRepository;

    final UserMapper userMapper;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto getUserByAccount(String account) {
        return userMapper.toDto(userRepository.findByAccount(account));
    }

    @Override
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return Lists.newArrayList(userMapper.toDto(users));
    }

    @Override
    public UserDto getById(UUID uuid) {
        User user = userRepository.findById(uuid).get();
        return userMapper.toDto(user);
    }

    @Override
    public UserDto save(CreateUserRequestDto dto) {
        User user = userMapper.createEntity(dto);

        //處理Roles
        if (!ObjectUtils.isEmpty(dto.getRoleIds())) setUserRoles(user, dto.getRoleIds());

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto update(UUID id, UpdateUserRequestDto dto) {
        User user = userRepository.findById(id).get();
        userMapper.updateEntity(user, dto);

        //處理Roles
        user.getRoles().clear();
        if (!ObjectUtils.isEmpty(dto.getRoleIds())) setUserRoles(user, dto.getRoleIds());

        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    private void setUserRoles(User user, List<String> roleIds) {
        List<UUID> uuidList = roleIds.stream()
                .map(roleId -> UUID.fromString(roleId))
                .collect(Collectors.toList());
        List<Role> roles = roleRepository.findAllByIdIn(uuidList);
        user.getRoles().addAll(roles);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isExist(UUID id) {
        return userRepository.existsById(id);
    }

//    @Override
//    public UserDto save(UserDto userDto) {
//        List<UUID> uuidList = userDto.getRoles().stream()
//                .map(roleId -> UUID.fromString(roleId))
//                .collect(Collectors.toList());
//
//        List<Role> roles = roleRepository.findAllByIdIn(uuidList);
//
//        User user = userMapper.toEntity(userDto);
//        user.setRoles(Sets.newHashSet(roles));
//        User savedUser = userRepository.save(user);
//        return userMapper.toDto(savedUser);
//    }

    @Override
    public PageableResult<UserDto> pageAll(Integer pageNumber, Integer rowsPerPage) {
        QueryParameter qp = new QueryParameter()
                .addPageNumber(pageNumber)
                .addRowsPerPage(rowsPerPage)
                .build();

        Page<User> resultPage = userRepository.findAll(
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
        return userRepository.existsByAccount(userAccount);
    }

    @Override
    public Collection<UserDto> getUsersOfRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).get();
        List<Role> roles = Collections.singletonList(role);
        Collection<User> users = userRepository.findAllByRolesIsIn(roles);
        return userMapper.toDto(users);
    }

}
