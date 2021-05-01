package idv.rennnhong.backendstarterkit.entity;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import idv.rennnhong.common.persistence.AuditableEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


import java.util.Set;

@Entity
@Table(name = "SysRole")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update sys_role set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class Role extends AuditableEntity<String> {


    public Role(String name, String code, Set<User> users, Set<RolePermission> rolePermissions) {
        this.name = name;
        this.code = code;
        this.users = users;
        this.rolePermissions = rolePermissions;
    }

    @Column(nullable = false)
    String name;

    @Column(nullable = false, unique = true)
    String code;

    @Column
    @ApiModelProperty(value = "邏輯删除（0 未删除、1 删除）")
    private Integer deleted = 0;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles")
    Set<User> users;

    @ElementCollection
    @CollectionTable(name = "sysRolePermission")
    Set<RolePermission> rolePermissions;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Role{");
        sb.append("name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append('}');
        return sb.toString();
    }
}
