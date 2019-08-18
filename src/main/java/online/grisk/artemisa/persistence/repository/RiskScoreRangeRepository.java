package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.RiskScore;
import online.grisk.artemisa.domain.entity.RiskScoreRange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RiskScoreRangeRepository extends JpaRepository<RiskScoreRange, Long> {
	Collection<RiskScoreRange> findAllByRiskScoreOrderByLowerLimit(RiskScore score);
}