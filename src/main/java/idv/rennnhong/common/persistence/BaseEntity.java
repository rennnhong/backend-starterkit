package idv.rennnhong.common.persistence;

import idv.rennnhong.common.persistence.audit.Auditable;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public abstract class BaseEntity<T> extends Auditable<T> {
}
