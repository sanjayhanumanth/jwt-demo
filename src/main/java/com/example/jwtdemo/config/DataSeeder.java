package com.example.jwtdemo.config;

import com.example.jwtdemo.entity.Product;
import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.repository.ProductRepository;
import com.example.jwtdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedProducts();
    }

    private void seedUsers() {
        if (userRepository.count() > 0) return;

        User admin = User.builder()
                .username("admin")
                .email("admin@example.com")
                .password(passwordEncoder.encode("password123"))
                .roles(Set.of("ROLE_ADMIN", "ROLE_USER"))
                .build();

        User user = User.builder()
                .username("user")
                .email("user@example.com")
                .password(passwordEncoder.encode("password123"))
                .roles(Set.of("ROLE_USER"))
                .build();

        User john = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password(passwordEncoder.encode("john1234"))
                .roles(Set.of("ROLE_USER"))
                .build();

        userRepository.saveAll(List.of(admin, user, john));
        log.info("✅ Seeded 3 users: admin / user / john_doe");
    }

    private void seedProducts() {
        if (productRepository.count() > 0) return;

        List<Product> products = List.of(
            Product.builder().name("Laptop Pro").description("High-performance laptop 16GB RAM").price(new BigDecimal("999.99")).stock(25).category("Electronics").build(),
            Product.builder().name("Wireless Mouse").description("Ergonomic wireless mouse").price(new BigDecimal("29.99")).stock(100).category("Electronics").build(),
            Product.builder().name("Mechanical Keyboard").description("RGB mechanical keyboard").price(new BigDecimal("79.99")).stock(60).category("Electronics").build(),
            Product.builder().name("Standing Desk").description("Height-adjustable standing desk").price(new BigDecimal("349.00")).stock(15).category("Furniture").build(),
            Product.builder().name("Monitor 27\"").description("4K UHD 27-inch monitor").price(new BigDecimal("499.99")).stock(30).category("Electronics").build(),
            Product.builder().name("Webcam HD").description("1080p HD webcam with mic").price(new BigDecimal("59.99")).stock(80).category("Electronics").build(),
            Product.builder().name("Desk Chair").description("Ergonomic office chair").price(new BigDecimal("249.00")).stock(20).category("Furniture").build(),
            Product.builder().name("USB-C Hub").description("7-in-1 USB-C hub").price(new BigDecimal("39.99")).stock(150).category("Accessories").build()
        );

        productRepository.saveAll(products);
        log.info("✅ Seeded {} products", products.size());
    }
}
