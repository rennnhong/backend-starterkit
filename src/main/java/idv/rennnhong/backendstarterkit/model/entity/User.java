package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import idv.rennnhong.common.persistence.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "SysUser")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql="update sys_user set deleted = 1 where id = ?")
@Where(clause = "deleted = 0")
public class User extends BaseEntity<String> {

    public User(String userName, String account, String password, Set<UserPermission> userPermissions,
                Set<Role> roles, String birthday, String gender, String email,
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

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;

    @Column
    private String userName;


    @Column(updatable = true)
    private String account;

    @Column
    @JsonIgnore
    private String password;

    @Column
    @ApiModelProperty(value = "邏輯删除（0 未删除、1 删除）")
    private Integer deleted = 0;


    @ElementCollection
    @CollectionTable(
        name = "sysUserPermission"
    )
    Set<UserPermission> userPermissions;

    @ManyToMany
    @JoinTable(name = "sysUserRole")
    private Set<Role> roles;


    @ManyToOne

    private Department department;

    @Column
    private String birthday;

    @Column
    private String gender;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String city;


}

