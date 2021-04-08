package idv.rennnhong.common;

import java.util.Collection;

public interface BaseService<D, ID> {

    Collection<D> getAll();

    D getById(ID id);

    D save(D t);

    D update(D t);

    void saveBatch(Collection<D> collection);

    void updateBatch(Collection<D> collection);

    void delete(ID id);

    boolean isExist(ID id);

    long countAll();
}
