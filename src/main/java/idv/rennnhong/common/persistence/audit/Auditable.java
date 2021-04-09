package idv.rennnhong.common.persistence.audit;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<T> {

    @CreatedBy//創建者
    @Column(updatable = false)
    protected T createUserId;

    @CreatedDate//創建日期
    @Column(updatable = false, nullable = false)
    protected Date createTime;

    @LastModifiedBy//修改者
    protected T updateUserId;

    @LastModifiedDate//修改日期
    @Column(nullable = false)
    protected Date updateTime;
}
