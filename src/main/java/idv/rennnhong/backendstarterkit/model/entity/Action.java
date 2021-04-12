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
public class Action extends AuditableEntity<String,UUID> {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return name.equals(action.name) &&
                permission.equals(action.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, permission);
    }
}
