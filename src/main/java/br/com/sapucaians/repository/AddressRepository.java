package br.com.sapucaians.repository;

import br.com.sapucaians.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value = "select * from account_addresses where account_id = ?1", nativeQuery = true)
    List<Address> findAllByUserId(String id);

    @Query(value = "SELECT a.* FROM addresses a, account_addresses a_addr, accounts acc where a.id = a_addr.address_id and a_addr.account_id = acc.id and acc.email = ?1", nativeQuery = true)
    List<Address> findAllByUserEmail(String email);
}
