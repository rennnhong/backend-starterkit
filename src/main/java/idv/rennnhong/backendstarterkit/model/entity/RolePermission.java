package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class RolePermission {

    @ManyToOne
    Permission permission;

    @ManyToOne
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
        return Objects.hash(permission.getId(), action.getId());
    }

    public RolePermission(Permission permission, Action action) {
        this.permission = permission;
        this.action = action;
    }
}
