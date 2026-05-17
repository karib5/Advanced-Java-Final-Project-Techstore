package com.techstore.controller;

import com.techstore.dto.request.OrderRequest;
import com.techstore.dto.response.ApiResponse;
import com.techstore.model.Order;
import com.techstore.model.OrderItem;
import com.techstore.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication) {
        Order order = orderService.placeOrder(authentication.getName(), request);
        return ResponseEntity.ok(ApiResponse.success("Order placed successfully", order));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<List<Order>>> getMyOrders(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success("Orders fetched",
                orderService.getMyOrders(authentication.getName())));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getAllOrders() {
        return ResponseEntity.ok(ApiResponse.success("All orders fetched",
                orderService.getAllOrders()));
    }

    @GetMapping("/{orderId}/items")
    public ResponseEntity<ApiResponse<List<OrderItem>>> getOrderItems(
            @PathVariable Long orderId) {
        return ResponseEntity.ok(ApiResponse.success("Order items fetched",
                orderService.getOrderItems(orderId)));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<Order>> updateStatus(@PathVariable Long orderId,
                                                           @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                orderService.updateOrderStatus(orderId, status)));
    }
}