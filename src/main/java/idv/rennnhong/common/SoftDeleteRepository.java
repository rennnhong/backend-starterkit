package idv.rennnhong.common;

import idv.rennnhong.common.persistence.BaseEntity;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface SoftDeleteRepository<T extends BaseEntity,ID> extends BaseRepository<T,ID>{
    @Override
    default void deleteInBatch(Iterable<T> entities) {
        throw new NotImplementedException();
    }

    @Override
    default void deleteAllInBatch(){
        throw new NotImplementedException();
    };

    @Override
    @Modifying
    @Query("update #{#entityName} e set e.deleted=1 where e.id=:id")
    void deleteById(@Param("id")ID id);

    @Override
    @Modifying
    @Query("update #{#entityName} e set e.deleted = 1 where e.id = ?#{#entity.id}")
    void delete(@Param("entity")T entity);

    @Override
    default void deleteAll(Iterable<? extends T> entities){
        throw new NotImplementedException();
    };

    @Override
    @Query("update #{#entityName} e set e.deleted=1")
    void deleteAll();
}
