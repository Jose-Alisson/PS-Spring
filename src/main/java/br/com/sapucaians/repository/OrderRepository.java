package br.com.sapucaians.repository;

import br.com.sapucaians.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>{

    @Query(value = "select * from orders where account_id = ?1", nativeQuery = true)
    List<Order> findAllByUserId(Long id);
}
