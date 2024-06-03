package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.ProductCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.ProductResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.ProductUpdateRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    DataResult<Product> getProductById(UUID productId);
    DataResult<ProductResponse> getProductByIdResponse(UUID productId);
    List<Product> getAllProduct(int page, int size);
    DataResult<List<ProductResponse>> getAllProductResponses(int page, int size);

    Result save(ProductCreateRequest request);
    Result save(Product product);

    Result updateProduct(UUID productId, ProductUpdateRequest request);

    Result deleteProduct(UUID productId);

}
