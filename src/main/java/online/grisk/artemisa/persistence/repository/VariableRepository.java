package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.DataIntegration;
import online.grisk.artemisa.domain.entity.Variable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface VariableRepository extends JpaRepository<Variable, Long> {
    void deleteAllByDataIntegrationCollection(Collection<DataIntegration> dataIntegrationCollection);
    List<Variable> findAllByBureau(boolean bureau);
}
