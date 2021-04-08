package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class UserPermission {

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

}
