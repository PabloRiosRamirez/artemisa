package online.grisk.artemisa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.grisk.artemisa.domain.entity.Ratio;

public interface RatioRepository extends JpaRepository<Ratio, Long> {
	
}
