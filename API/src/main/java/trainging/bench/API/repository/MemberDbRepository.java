package trainging.bench.API.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import trainging.bench.API.model.MemberDb;

@Repository
public interface MemberDbRepository extends JpaRepository<MemberDb, Long> {
}
