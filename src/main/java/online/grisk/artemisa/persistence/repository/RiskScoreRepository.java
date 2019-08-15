package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.RiskScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskScoreRepository extends JpaRepository<RiskScore, Long> {
    void deleteAllByOrganization(Long organization);

    RiskScore findScoreByOrganization(Long idScore);
}
