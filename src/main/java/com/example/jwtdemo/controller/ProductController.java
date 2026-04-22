package com.example.jwtdemo.controller;

import com.example.jwtdemo.dto.MessageResponse;
import com.example.jwtdemo.dto.ProductRequest;
import com.example.jwtdemo.entity.Product;
import com.example.jwtdemo.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "📦 Products", description = "CRUD for products — GET needs USER role, write operations need ADMIN role")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductRepository productRepository;

    @GetMapping
    @Operation(summary = "List all products", description = "Returns all products. Requires ROLE_USER or ROLE_ADMIN.")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "Product ID", example = "1") @PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name")
    public ResponseEntity<List<Product>> searchProducts(
            @Parameter(description = "Name keyword", example = "Laptop") @RequestParam String name) {
        return ResponseEntity.ok(productRepository.findByNameContainingIgnoreCase(name));
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get products by category")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Category name", example = "Electronics") @PathVariable String category) {
        return ResponseEntity.ok(productRepository.findByCategory(category));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create product", description = "Create a new product. Requires ROLE_ADMIN.")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .category(request.getCategory())
                .build();
        return ResponseEntity.ok(productRepository.save(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update product", description = "Update an existing product. Requires ROLE_ADMIN.")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                  @Valid @RequestBody ProductRequest request) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(request.getName());
                    existing.setDescription(request.getDescription());
                    existing.setPrice(request.getPrice());
                    existing.setStock(request.getStock());
                    existing.setCategory(request.getCategory());
                    return ResponseEntity.ok(productRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete product", description = "Delete a product by ID. Requires ROLE_ADMIN.")
    public ResponseEntity<MessageResponse> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Product " + id + " deleted successfully."));
    }


    @GetMapping("/random")
    @Operation(summary = "Random")
    public ResponseEntity<String> random(
            @Parameter(description = "Name ", example = "Laptop") @RequestParam String name) {
        return ResponseEntity.ok("Hi" + name);
    }
}
