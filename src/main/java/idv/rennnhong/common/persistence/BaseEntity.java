package idv.rennnhong.common.persistence;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<ID>{

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    protected ID id;

}
