package com.techstore.controller;

import com.techstore.dto.request.ProductRequest;
import com.techstore.dto.response.ApiResponse;
import com.techstore.model.Product;
import com.techstore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Products fetched",
                productService.getAllProducts()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Product fetched",
                productService.getProductById(id)));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse<List<Product>>> getByCategory(
            @PathVariable Long categoryId) {
        return ResponseEntity.ok(ApiResponse.success("Products fetched",
                productService.getProductsByCategory(categoryId)));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Product>>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(ApiResponse.success("Search results",
                productService.searchByName(keyword)));
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Product>>> filterByPrice(
            @RequestParam BigDecimal maxPrice) {
        return ResponseEntity.ok(ApiResponse.success("Filtered products",
                productService.filterByMaxPrice(maxPrice)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Product>> create(
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Product created",
                productService.createProduct(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Product>> update(@PathVariable Long id,
                                                       @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Product updated",
                productService.updateProduct(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ApiResponse.success("Product deleted", null));
    }
}
