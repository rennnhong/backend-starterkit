package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class RolePermission {

    @ManyToOne
//    @JoinColumn(
//        name = "permission_id"
//    )
    Permission permission;

    @ManyToOne
//    @JoinColumn(
//        name = "action_id"
//    )
    Action action;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolePermission rolePermission = (RolePermission) o;
        return permission.equals(rolePermission.permission) &&
            action.equals(rolePermission.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission, action);
    }

    public RolePermission(Permission permission, Action action) {
        this.permission = permission;
        this.action = action;
    }
}
