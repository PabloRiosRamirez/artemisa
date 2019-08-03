package online.grisk.artemisa.persistence.repository;

import online.grisk.artemisa.domain.entity.TypeVariable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeVariableRepository extends JpaRepository<TypeVariable, Long> {

    TypeVariable findByCode(String code);

}
