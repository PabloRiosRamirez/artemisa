package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.BusinessTree;
import online.grisk.artemisa.domain.entity.RiskScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessTreeRepository extends JpaRepository<BusinessTree, Long> {
    void deleteAllByOrganization(Long organization);
    BusinessTree findBusinessTreeByOrganization(Long idBusinessTree);
}
