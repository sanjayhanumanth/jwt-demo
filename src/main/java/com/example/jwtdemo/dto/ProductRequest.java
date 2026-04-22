package com.example.jwtdemo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Product create/update request")
public class ProductRequest {

    @NotBlank
    @Schema(description = "Product name", example = "Laptop Pro")
    private String name;

    @Schema(description = "Product description", example = "High-performance laptop")
    private String description;

    @NotNull
    @Positive
    @Schema(description = "Price", example = "999.99")
    private BigDecimal price;

    @NotNull
    @PositiveOrZero
    @Schema(description = "Stock quantity", example = "50")
    private Integer stock;

    @Schema(description = "Category", example = "Electronics")
    private String category;
}
