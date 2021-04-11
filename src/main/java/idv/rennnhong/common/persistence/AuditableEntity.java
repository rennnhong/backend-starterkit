package idv.rennnhong.common.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<T, ID> extends BaseEntity<ID> {

    @CreatedBy//創建者
    @Column(updatable = false)
    protected String createUserId;

    @CreatedDate//創建日期
    @Column(updatable = false, nullable = false)
    protected Date createTime;

    @LastModifiedBy//修改者
    protected String updateUserId;

    @LastModifiedDate//修改日期
    @Column(nullable = false)
    protected Date updateTime;
}
