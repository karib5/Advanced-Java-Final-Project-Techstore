package com.techstore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("order_items")
public class OrderItem {
    @Id
    private Long itemId;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}