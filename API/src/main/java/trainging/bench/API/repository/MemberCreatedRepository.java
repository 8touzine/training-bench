package trainging.bench.API.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import trainging.bench.API.model.MemberEvent;

@Repository
public interface MemberCreatedRepository extends ElasticsearchRepository<MemberEvent, String> {

}
