package idv.rennnhong.backendstarterkit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import idv.rennnhong.common.persistence.AuditableEntity;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "SysPermission")
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Permission extends AuditableEntity<String> {

    public Permission(String name, String icon, String route, Integer sorted, Integer opened, Set<Action> actions,
                      Permission parent, Set<Permission> children) {
        this.name = name;
        this.icon = icon;
        this.route = route;
        this.sorted = sorted;
        this.opened = opened;
        this.actions = actions;
        this.parent = parent;
        this.children = children;
    }

    @Column(nullable = false)
    String name;

    @Column
    String icon;

    @Column
    String route;

    @Column
    Integer sorted;

    @Column
    Integer opened;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    Set<Action> actions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    Permission parent;

    @OneToMany(mappedBy = "parent")
    Set<Permission> children;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Permission{");
        sb.append("name='").append(name).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", route='").append(route).append('\'');
        sb.append(", sorted=").append(sorted);
        sb.append(", opened=").append(opened);
        sb.append(", parent=").append(parent.getId());
        sb.append('}');
        return sb.toString();
    }
}
