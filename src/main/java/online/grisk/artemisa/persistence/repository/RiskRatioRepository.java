package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.RiskRatio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RiskRatioRepository extends JpaRepository<RiskRatio, Long> {
    void deleteAllByOrganization(Long organization);
    RiskRatio findRiskRatioByOrganization(Long idRiskRatio);
}
