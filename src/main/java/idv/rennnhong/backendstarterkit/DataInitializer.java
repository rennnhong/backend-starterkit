package idv.rennnhong.backendstarterkit;

import com.github.javafaker.Faker;
import com.google.common.collect.Lists;
import idv.rennnhong.backendstarterkit.model.dao.ActionDao;
import idv.rennnhong.backendstarterkit.model.dao.ApiDao;
import idv.rennnhong.backendstarterkit.model.dao.PermissionDao;
import idv.rennnhong.backendstarterkit.model.dao.RoleDao;
import idv.rennnhong.backendstarterkit.model.dao.UserDao;
import idv.rennnhong.backendstarterkit.model.entity.Action;
import idv.rennnhong.backendstarterkit.model.entity.Permission;
import idv.rennnhong.backendstarterkit.model.entity.Role;
import idv.rennnhong.backendstarterkit.model.entity.RolePermission;
import idv.rennnhong.backendstarterkit.model.entity.User;
import idv.rennnhong.backendstarterkit.service.ActionService;
import idv.rennnhong.backendstarterkit.service.ApiService;
import idv.rennnhong.backendstarterkit.service.PermissionService;
import idv.rennnhong.backendstarterkit.service.RoleService;
import idv.rennnhong.backendstarterkit.service.UserService;
import idv.rennnhong.backendstarterkit.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class DataInitializer {

    @Autowired
    RoleService roleService;
    @Autowired
    PermissionService permissionService;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleDao roleDao;
    @Autowired
    PermissionDao permissionDao;
    @Autowired
    ActionDao actionDao;
    @Autowired
    ApiDao apiDao;

    Faker fakerCN = new Faker(new Locale("zh-TW"));

    Faker fakerEN = new Faker(new Locale("en_us"));

    @PostConstruct
    public void init() {

        List<Role> initRoles = Lists.newArrayList(
            new Role("系統管理員", "admin", null, new HashSet<>()),
            new Role("作業管理員", "manager", null, new HashSet<>()),
            new Role("一般使用者", "user", null, new HashSet<>())
        );

        Permission 根目錄 = new Permission("根目錄", null, null, 1, 1, null, null, null);
        Permission 目錄1 = new Permission("目錄1", null, null, 1, 1, null, 根目錄, null);
        Permission 目錄2 = new Permission("目錄2", null, null, 1, 1, null, 根目錄, null);
        Permission 目錄3 = new Permission("目錄3", null, null, 1, 1, null, 根目錄, null);
        Permission 目錄1_1 = new Permission("目錄1_1", null, null, 1, 1, null, 目錄1, null);
        Permission 目錄2_1 = new Permission("目錄2_1", null, null, 1, 1, null, 目錄2, null);
        Permission 目錄3_1 = new Permission("目錄3_1", null, null, 1, 1, null, 目錄3, null);

        List<Permission> initPermissions = Lists.newArrayList(
            根目錄,
            目錄1,
            目錄2,
            目錄3,
            目錄1_1,
            目錄2_1,
            目錄3_1
        );

        String[] actionStr = new String[]{"新增", "修改", "刪除", "查詢"};

        for (Permission initPermission : initPermissions) {
            Set<Action> collect = Arrays.stream(actionStr).map(str -> new Action(str, null, null, null, null, null))
                .collect(Collectors.toSet());
            initPermission.setActions(collect);
        }

        permissionDao.saveAll(initPermissions);

        for (Role initRole : initRoles) {
            for (Permission initPermission : initPermissions) {
                for (Action action : initPermission.getActions()) {
                    if ("系統管理員".equals(initRole.getName())
                        && permissionService.isLastLayer(initPermission.getId().toString())
                        && ("新增".equals(action.getName()) || "修改".equals(action.getName()) || "刪除"
                        .equals(action.getName()) || "查詢".equals(action.getName()))) {
                        initRole.getRolePermissions().add(new RolePermission(initPermission, action));
                    }
                    if ("作業管理員".equals(initRole.getName())
                        && permissionService.isLastLayer(initPermission.getId().toString())
                        && ("新增".equals(action.getName()) || "修改".equals(action.getName()) || "查詢"
                        .equals(action.getName()))) {
                        initRole.getRolePermissions().add(new RolePermission(initPermission, action));
                    }
                    if ("一般使用者".equals(initRole.getName())
                        && permissionService.isLastLayer(initPermission.getId().toString())
                        && "查詢".equals(action.getName())) {
                        initRole.getRolePermissions().add(new RolePermission(initPermission, action));
                    }

                }
            }
        }

        List<User> initUsers = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            User user = new User(
                fakerCN.name().fullName(),
                fakerEN.name().firstName() + fakerEN.number().digits(5),
                fakerCN.crypto().md5(),
                null,
                new HashSet(),
                null,
                fakerCN.date().birthday().toString(),
                null,
                null,
                fakerCN.phoneNumber().phoneNumber(),
                fakerCN.country().capital());
            int random = (int) (Math.random() * 3);
            user.getRoles().add(initRoles.get(random));
            initUsers.add(user);
        }
        roleDao.saveAll(initRoles);
        userDao.saveAll(initUsers);

    }


}
