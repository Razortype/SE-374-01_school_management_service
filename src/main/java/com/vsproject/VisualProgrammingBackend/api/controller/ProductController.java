package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.ProductCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.ProductResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.ProductUpdateRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ProductService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<DataResult<List<ProductResponse>>> getAllProductResponse(@RequestParam int page, @RequestParam int size) {

        DataResult<List<ProductResponse>> result = productService.getAllProductResponses(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @GetMapping("/{product-id}")
    public ResponseEntity<DataResult<ProductResponse>> getProductResponseById(@PathVariable(name = "product-id") UUID productId) {

        DataResult<ProductResponse> result = productService.getProductByIdResponse(productId);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping
    public ResponseEntity<Result> createProduct(@RequestBody ProductCreateRequest request) {

        Result result = productService.save(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("/{product-id}")
    public ResponseEntity<Result> updateProduct(@PathVariable(name = "product-id") UUID productId,
                                                                     @RequestBody ProductUpdateRequest request) {

        Result result = productService.updateProduct(productId, request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<Result> deleteProduct(@PathVariable(name = "product-id") UUID productId) {

        Result result = productService.deleteProduct(productId);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
