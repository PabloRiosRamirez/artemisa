package online.grisk.artemisa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.grisk.artemisa.domain.entity.DataIntegration;

@Repository
public interface DataIntegrationRepository extends JpaRepository<DataIntegration, Long>{
    void deleteAllByOrganization(Long organization);

    DataIntegration findDataIntegrationsByOrganization(Long idDataIntegration);
}
