package online.grisk.artemisa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import online.grisk.artemisa.domain.entity.Ratio;

public interface RatioRepository extends JpaRepository<Ratio, Long> {
	
	@Query("DELETE FROM Ratio r where r.riskRatio.idRiskRatio = ?1")
	void deleteByIdRiskRatio(long idRiskRatio);
}
