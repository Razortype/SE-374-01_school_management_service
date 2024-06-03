package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.ProductResponse;
import com.vsproject.VisualProgrammingBackend.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUtil {

    public List<ProductResponse> mapToProductResponses(List<Product> products) {

        return products.stream()
                .map(this::mapToProductResponse)
                .toList();

    }

    public ProductResponse mapToProductResponse(Product product) {

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .transactionAmount(product.getTransaction().size())
                .build();

    }

}
