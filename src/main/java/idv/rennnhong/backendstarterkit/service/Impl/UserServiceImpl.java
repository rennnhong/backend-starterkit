package idv.rennnhong.backendstarterkit.service.Impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.common.BaseServiceImpl;
import idv.rennnhong.common.query.PageableResult;
import idv.rennnhong.common.query.PageableResultImpl;
import idv.rennnhong.common.query.QueryParameter;
import idv.rennnhong.backendstarterkit.model.dao.UserDao;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.backendstarterkit.dto.UserDto;
import idv.rennnhong.backendstarterkit.dto.mapper.UserMapper;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl extends BaseServiceImpl<UserDto, User, UUID> implements UserService {

    final UserDao userDao;

    final UserMapper userMapper;

    @Autowired
    RoleDao roleDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, UserMapper userMapper) {
        super(userDao, userMapper);
        this.userDao = userDao;
        this.userMapper = userMapper;
    }


    @Override
    public UserDto getUserByAccount(String account) {
        return userMapper.toDto(userDao.findByAccount(account));
    }

//    @Override
//    public KdQueryResult<UserDto> searchUser(SearchUserRequestDto searchUserRequestDto) {
//        return null;
//    }

    @Override
    public UserDto save(UserDto userDto) {
        List<UUID> uuidList = Arrays.stream(userDto.getRoleIds())
                .map(roleId -> UUID.fromString(roleId))
                .collect(Collectors.toList());

        List<Role> roles = roleDao.findAllByIdIn(uuidList);

        User user = userMapper.toEntity(userDto);
        user.setRoles(Sets.newHashSet(roles));
        User savedUser = userDao.save(user);
        return userMapper.toDto(savedUser);
    }

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
    public boolean isLoginStatus(String account, String password) {
        return false;
    }

    @Override
    public boolean changeUserPassword(String userId, String password) {
        return false;
    }

    @Override
    public boolean isExistByAccount(String userAccount) {
        return userDao.existsByAccount(userAccount);
    }

}
