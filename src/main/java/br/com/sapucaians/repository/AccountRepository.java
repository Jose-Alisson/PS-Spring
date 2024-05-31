package br.com.sapucaians.repository;

import br.com.sapucaians.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long>{
	
	@Query(value = "SELECT * FROM accounts WHERE email = ?1", nativeQuery = true)
	Optional<Account> findByEmail(String email);

	@Query(value = "select exists(select 1 from accounts a where a.email = ?1)", nativeQuery = true)
	long existByEmail(String email);

	@Query(value = "select id from accounts where email = ?1", nativeQuery = true)
	Long getIdByEmail(String email);
}
