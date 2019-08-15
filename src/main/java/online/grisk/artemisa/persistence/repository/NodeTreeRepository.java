package online.grisk.artemisa.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import online.grisk.artemisa.domain.entity.NodeTree;

public interface NodeTreeRepository extends JpaRepository<NodeTree, Long> {
	@Query("DELETE FROM NodeTree n where n.tree.idTree = ?1")
	void deleteByIdBusinessTree(long idBusinessTree);
}
