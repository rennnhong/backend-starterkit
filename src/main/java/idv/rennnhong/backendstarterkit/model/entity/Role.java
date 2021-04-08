package idv.rennnhong.backendstarterkit.model.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import idv.rennnhong.common.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SysRole")
//@Data
@Getter
@Setter
@ToString(exclude = "users")
@EqualsAndHashCode(exclude = "users")
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity<String> {

    public Role(String name, String code, Set<User> users, Set<RolePermission> rolePermissions) {
        this.name = name;
        this.code = code;
        this.users = users;
        this.rolePermissions = rolePermissions;
    }

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String code;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    Set<User> users;

//    @ManyToMany
//    @JoinTable(name = "sys_role_page",
//            joinColumns = {@JoinColumn(name = "role_id")},
//            inverseJoinColumns = {@JoinColumn(name = "permission_id")})
//    Set<Page> pages;

    @ElementCollection
    @CollectionTable(
        name = "sysRolePermission"
//        joinColumns = @JoinColumn(name = "role_id")
    )
    Set<RolePermission> rolePermissions;
}
