package idv.rennnhong.common;

import java.util.Collection;

public abstract class BaseServiceImpl<DTO, T, ID> implements BaseService<DTO, ID> {

    protected final BaseDao<T, ID> baseDao;
    protected final BaseMapper<DTO, T> baseMapper;

    public BaseServiceImpl(BaseDao<T, ID> baseDao, BaseMapper<DTO, T> baseMapper) {
        this.baseDao = baseDao;
        this.baseMapper = baseMapper;
    }


    @Override
    public Collection<DTO> getAll() {
        return baseMapper.toDto(baseDao.findAll());
    }

    @Override
    public DTO getById(ID id) {
        return baseMapper.toDto(baseDao.findById(id).get());
    }

    @Override
    public DTO save(DTO t) {
        //todo 不可包含id
        T entity = baseMapper.toEntity(t);
        return baseMapper.toDto(baseDao.save(entity));
    }

    @Override
    public DTO update(DTO t) {
        //todo 需包含id
        T entity = baseMapper.toEntity(t);
        return baseMapper.toDto(baseDao.save(entity));
    }

    @Override
    public void saveBatch(Collection<DTO> collection) {
        Collection<T> entities = baseMapper.toEntity(collection);
        baseDao.saveAll(entities);
    }

    @Override
    public void updateBatch(Collection<DTO> collection) {
        Collection<T> entities = baseMapper.toEntity(collection);
        baseDao.saveAll(entities);
    }

    @Override
    public void delete(ID id) {
        baseDao.deleteById(id);
    }

    @Override
    public boolean isExist(ID id) {
        return baseDao.existsById(id);
    }

    @Override
    public long countAll() {
        return baseDao.count();
    }
}
