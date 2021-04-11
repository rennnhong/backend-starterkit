package idv.rennnhong.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseDao<T,ID> extends JpaRepository<T, ID>{

}
