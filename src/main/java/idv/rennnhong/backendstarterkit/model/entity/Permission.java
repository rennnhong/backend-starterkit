package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import idv.rennnhong.common.persistence.AuditableEntity;
import idv.rennnhong.common.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "SysPermission")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Permission extends AuditableEntity<String,UUID> {

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

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID id;

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

    @OneToMany(cascade = CascadeType.PERSIST)
    Set<Action> actions;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pid")
    Permission parent;

    @OneToMany(mappedBy = "parent")
    Set<Permission> children;

}
