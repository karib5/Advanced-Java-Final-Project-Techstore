package com.techstore.repository;

import com.techstore.model.Order;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    @Query("SELECT * FROM orders WHERE user_id = :userId")
    List<Order> findByUserId(Long userId);
}