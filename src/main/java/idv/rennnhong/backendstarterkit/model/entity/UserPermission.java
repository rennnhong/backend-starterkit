package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class UserPermission {

    @ManyToOne
    Permission permission;

    @ManyToOne
    Action action;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPermission that = (UserPermission) o;
        return Objects.equals(permission, that.permission) &&
                Objects.equals(action, that.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission, action);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserPermission{");
        sb.append("permission=").append(permission);
        sb.append(", action=").append(action);
        sb.append('}');
        return sb.toString();
    }
}
