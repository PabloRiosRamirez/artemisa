package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.entity.ScoreRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ScoreRangeRepository extends JpaRepository<ScoreRange, Long> {
	Collection<ScoreRange> findAllByScoreOrderByLowerLimit(RiskScore score);
}