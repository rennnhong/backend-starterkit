package idv.rennnhong.common.persistence;

import org.hibernate.annotations.Type;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Type(type="uuid-char")
    protected UUID id = UUID.randomUUID();

    @Version
    int version;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;

        if (id == that.getId()) return true;
        if (id == null) return false;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
