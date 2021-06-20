package echoic.linkgenerator.repos.mongo;

import echoic.linkgenerator.core.MusicEntity;
import org.springframework.data.mongodb.repository.CountQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicEntityRepo extends MongoRepository<MusicEntity, Long>
{
    @Query("{'code' : ?0}")
    List<MusicEntity> findByCode(Long code);

    @CountQuery("{'code' : ?0}")
    Long countByCode(Long code);
}
