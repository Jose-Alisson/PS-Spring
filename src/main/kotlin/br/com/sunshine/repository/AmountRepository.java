package br.com.sunshine.repository;

import br.com.sunshine.model.Amount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AmountRepository extends JpaRepository<Amount, Long>{
	
	@Query(value = "select * from amounts where account_id = ?1 and status = 'DESANEXADO'", nativeQuery = true)
	List<Amount> findAllByUserId(Long id);

	@Modifying
	@Transactional
	@Query(value = "update amounts set status = 'ANEXADO' where id in ?1", nativeQuery = true)
	void attachAll(List<String> ids);

	/*
	@Modifying
	@Transactional
	@Query(value = "update amounts set quantity = quantity - ?2 where id = ?1 and quantity > 0", nativeQuery = true)
	void decrement(String id, int value);


	@Modifying
	@Transactional
	@Query(value = "update amounts set quantity = quantity + ?2 where id = ?1 and quantity >= 0", nativeQuery = true)
	void increment(String id, int value);


	 */


	@Query(value = "call decrement(?1, ?2)", nativeQuery = true)
	int decrement(String id, int value);

	@Query(value = "call increment(?1, ?2)", nativeQuery = true)
	int increment(String id, int value);
}
