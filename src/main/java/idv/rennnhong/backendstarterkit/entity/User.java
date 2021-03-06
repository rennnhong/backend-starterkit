package idv.rennnhong.backendstarterkit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import idv.rennnhong.common.persistence.AuditableEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "SysUser")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "update sys_user set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class User extends AuditableEntity<String> {


    public User(String userName, String account, String password, Set<UserPermission> userPermissions,
                Set<Role> roles, LocalDate birthday, String gender, String email,
                String phone, String city) {
        this.userName = userName;
        this.account = account;
        this.password = password;
        this.userPermissions = userPermissions;
        this.roles = roles;
        this.birthday = birthday;
        this.gender = gender;
        this.email = email;
        this.phone = phone;
        this.city = city;
    }

    @Column
    private String userName;

    @Column(nullable = false, updatable = false, unique = true)
    private String account;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column
    @ApiModelProperty(value = "邏輯删除（0 未删除、1 删除）")
    private Integer deleted = 0;

    @ElementCollection
    @CollectionTable(name = "sysUserPermission")
    Set<UserPermission> userPermissions;

    @ManyToMany
    @JoinTable(name = "sysUserRole")
    private Set<Role> roles;

    @Column
    private LocalDate birthday;

    @Column
    private String gender;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String city;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("userName='").append(userName).append('\'');
        sb.append(", account='").append(account).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", deleted=").append(deleted);
        sb.append(", birthday=").append(birthday);
        sb.append(", gender='").append(gender).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

