package idv.rennnhong.backendstarterkit.repository;

import idv.rennnhong.backendstarterkit.model.entity.Api;
import idv.rennnhong.common.BaseRepository;

import java.util.Optional;
import java.util.UUID;

public interface ApiRepository extends BaseRepository<Api, UUID> {

    Optional<Api> findByUrlAndHttpMethod(String uri, String httpMethod);

}
