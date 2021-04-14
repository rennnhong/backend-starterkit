package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import idv.rennnhong.common.persistence.AuditableEntity;
import idv.rennnhong.common.persistence.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "SysAction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Action extends AuditableEntity<String> {

    public Action(String name, String icon, Integer sorted, Integer disabled, String functionName, Api api) {
        this.name = name;
        this.icon = icon;
        this.sorted = sorted;
        this.disabled = disabled;
        this.functionName = functionName;
        this.api = api;
    }

    @Column(nullable = false)
    String name;

    @Column
    String icon;

    @Column
    Integer sorted;

    @Column
    Integer disabled;

    @Column
    String functionName;

    @ManyToOne
    Api api;

    @ManyToOne
    Permission permission;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Action{");
        sb.append("name='").append(name).append('\'');
        sb.append(", icon='").append(icon).append('\'');
        sb.append(", sorted=").append(sorted);
        sb.append(", disabled=").append(disabled);
        sb.append(", functionName='").append(functionName).append('\'');
        sb.append(", api=").append(api);
        sb.append(", permission=").append(permission.getId());
        sb.append('}');
        return sb.toString();
    }
}
