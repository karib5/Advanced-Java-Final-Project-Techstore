package com.techstore.repository;

import com.techstore.model.Product;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT * FROM products WHERE category_id = :categoryId")
    List<Product> findByCategoryId(Long categoryId);
}