package com.techstore.service;

import com.techstore.dto.request.ProductRequest;
import com.techstore.exception.ResourceNotFoundException;
import com.techstore.model.Product;
import com.techstore.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .toList();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
    }

    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    // Search products by name (case-insensitive) using streams
    public List<Product> searchByName(String keyword) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Filter products under a max price using streams
    public List<Product> filterByMaxPrice(BigDecimal maxPrice) {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .filter(p -> p.getPrice().compareTo(maxPrice) <= 0)
                .toList();
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategoryId(request.getCategoryId());
        product.setCreatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequest request) {
        Product existing = getProductById(id);
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStockQuantity(request.getStockQuantity());
        existing.setCategoryId(request.getCategoryId());
        return productRepository.save(existing);
    }

    public void deleteProduct(Long id) {
        getProductById(id);
        productRepository.deleteById(id);
    }
}
