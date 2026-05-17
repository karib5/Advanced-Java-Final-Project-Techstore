package com.techstore.service;

import com.techstore.dto.request.OrderRequest;
import com.techstore.exception.ResourceNotFoundException;
import com.techstore.model.Order;
import com.techstore.model.OrderItem;
import com.techstore.model.Product;
import com.techstore.model.User;
import com.techstore.repository.OrderItemRepository;
import com.techstore.repository.OrderRepository;
import com.techstore.repository.ProductRepository;
import com.techstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Order placeOrder(String username, OrderRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        BigDecimal total = BigDecimal.ZERO;

        for (OrderRequest.OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found: " + item.getProductId()));
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for: " + product.getName());
            }
            total = total.add(product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        Order order = new Order();
        order.setUserId(user.getUserId());
        order.setTotalAmount(total);
        order.setStatus("PENDING");
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        for (OrderRequest.OrderItemRequest item : request.getItems()) {
            Product product = productRepository.findById(item.getProductId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(savedOrder.getOrderId());
            orderItem.setProductId(item.getProductId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItemRepository.save(orderItem);
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        return savedOrder;
    }

    public List<Order> getMyOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return orderRepository.findByUserId(user.getUserId());
    }

    public List<Order> getAllOrders() {
        return StreamSupport.stream(orderRepository.findAll().spliterator(), false)
                .toList();
    }

    public List<OrderItem> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + orderId));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
