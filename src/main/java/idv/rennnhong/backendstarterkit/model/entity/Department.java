package idv.rennnhong.backendstarterkit.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "SysDepartment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class Department extends AuditableEntity<String,UUID> {

    @Column(nullable = false)
    String masterId;

    @Column(nullable = false)
    String name;

    @Column
    Integer level;

    @Column
    String code;

    @Column
    String shortName;

    @Column
    String shortCode;

    @Column
    String fullName;

    @Column
    String fullCode;

    @Column
    String preFullCode;

    @Column
    String phone;

    @Column
    String fax;

    @Column
    String zip;

    @Column
    String address;

    @Column
    String email;

    @Column
    String notes;

    @Column
    Boolean isOpen;

    @Column
    Integer orderNumber;
}
