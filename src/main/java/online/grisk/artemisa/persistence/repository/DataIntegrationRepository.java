package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.Dataintegration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataIntegrationRepository extends JpaRepository<Dataintegration, Long> {
    void deleteAllByOrganization(Long organization);

    Dataintegration findDataIntegrationsByOrganization(Long idDataIntegration);
}
