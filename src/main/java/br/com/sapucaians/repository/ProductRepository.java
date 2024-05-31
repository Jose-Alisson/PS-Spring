package br.com.sapucaians.repository;

import br.com.sapucaians.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT COUNT(*) AS total FROM products", nativeQuery = true)
    int size();

    @Query(value = "SELECT * FROM products LIMIT 24 OFFSET ?1", nativeQuery = true)
    List<Product> getByOffSet(int offset);

    @Query(value = "select * from products where lower(product_name) like lower(concat('%', ?1, '%'))", nativeQuery = true)
    List<Product> search(String s);
}
