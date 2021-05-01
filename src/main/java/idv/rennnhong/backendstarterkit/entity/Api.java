package idv.rennnhong.backendstarterkit.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import idv.rennnhong.common.persistence.AuditableEntity;
import lombok.*;

@Entity
@Table(name = "SysApi")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Api extends AuditableEntity<String> {

    @Column(nullable = false)
    String url;

    @Column(nullable = false)
    String httpMethod;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Api{");
        sb.append("url='").append(url).append('\'');
        sb.append(", httpMethod='").append(httpMethod).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
