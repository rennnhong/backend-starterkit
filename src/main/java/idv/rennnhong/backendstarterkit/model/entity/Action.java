package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import idv.rennnhong.common.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import java.util.UUID;

@Entity
@Table(name = "SysAction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Action extends BaseEntity<String> {

    public Action(String name, String icon, Integer sorted, Integer disabled, String functionName, Api api) {
        this.name = name;
        this.icon = icon;
        this.sorted = sorted;
        this.disabled = disabled;
        this.functionName = functionName;
        this.api = api;
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
    Integer sorted;

    @Column
    Integer disabled;

    @Column
    String functionName;

    //    @Column
//    String apiId;
    @ManyToOne
//    @JoinColumn(
//        name = "api_id"
//    )
    Api api;

    @ManyToOne
//    @JoinColumn(
//        name = "permission_id"
//    )
    Permission permission;
}
