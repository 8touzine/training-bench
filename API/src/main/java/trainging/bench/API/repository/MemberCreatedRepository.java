package trainging.bench.API.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import trainging.bench.API.model.MemberEvent;

public interface MemberCreatedRepository extends ElasticsearchRepository<MemberEvent, String> {

}
