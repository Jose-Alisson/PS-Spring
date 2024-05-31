package br.com.sapucaians.repository;

import br.com.sapucaians.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AttributeRepository extends JpaRepository<Attribute, Long> {

	@Modifying
	@Transactional
	@Query(value = "delete ppa.* from product_product_attribute ppa where ppa.attribute_id = ?1", nativeQuery = true)
	void rrp(Long id);
	
	@Query(value = "select pa.* from product_attribute pa where pa.id in (select ppr.attribute_id from product_product_attribute ppr where ppr.product_id = ?1)", nativeQuery = true)
	List<Attribute> findAllByProductId(Long id);
}
