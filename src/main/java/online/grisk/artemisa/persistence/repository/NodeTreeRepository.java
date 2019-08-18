package online.grisk.artemisa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.grisk.artemisa.domain.entity.BusinessTreeNode;

public interface NodeTreeRepository extends JpaRepository<BusinessTreeNode, Long> {
	
}
