package com.techstore.repository;

import com.techstore.model.OrderItem;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {

    @Query("SELECT * FROM order_items WHERE order_id = :orderId")
    List<OrderItem> findByOrderId(Long orderId);
}