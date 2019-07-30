package online.grisk.artemisa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import online.grisk.artemisa.domain.entity.DataIntegration;

@Repository
public interface IDataIntegrationRepository extends JpaRepository<DataIntegration, Long>{
//    Iterable<DataIntegration> findDataIntegrationsByOrganization(Long organization);
    void deleteAllByOrganization(Long organization);

}
