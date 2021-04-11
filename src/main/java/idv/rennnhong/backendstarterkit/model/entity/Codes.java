package idv.rennnhong.backendstarterkit.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import idv.rennnhong.common.persistence.AuditableEntity;
import idv.rennnhong.common.persistence.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import java.util.UUID;

@Entity
@Table(name = "SysCodes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Codes extends AuditableEntity<String,UUID> {

//    @Id
//    @GeneratedValue
//    @Type(type="uuid-char")
//    private UUID id;

    @Column(nullable = false)
    String masterId;

    @Column(nullable = false)
    String name;

    @Column
    String codeKind;

    @Column
    String codeNo;

    @Column
    String codePrefix;
}
