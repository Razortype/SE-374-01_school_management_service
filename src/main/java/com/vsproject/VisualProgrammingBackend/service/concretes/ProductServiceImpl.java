package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.ProductCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.ProductResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.ProductUpdateRequest;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.ProductUtil;
import com.vsproject.VisualProgrammingBackend.entity.Product;
import com.vsproject.VisualProgrammingBackend.repository.ProductRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductUtil productUtil;

    @Override
    public DataResult<Product> getProductById(UUID productId) {

        Product product = productRepository.findById(productId).orElse(null);
        if (product == null) {
            return new ErrorDataResult<>("Product not found: " + productId);
        }
        return new SuccessDataResult<>(product, "Product found");

    }

    @Override
    public DataResult<ProductResponse> getProductByIdResponse(UUID productId) {

        DataResult<Product> productResult = getProductById(productId);
        if (!productResult.isSuccess()) {
            return new ErrorDataResult<>(productResult.getMessage());
        }
        Product product = productResult.getData();

        ProductResponse response = productUtil.mapToProductResponse(product);
        return new SuccessDataResult<>(response, "Product found");

    }

    @Override
    public List<Product> getAllProduct(int page, int size) {

        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return products.toList();

    }

    @Override
    public DataResult<List<ProductResponse>> getAllProductResponses(int page, int size) {

        List<Product> products = getAllProduct(page, size);
        List<ProductResponse> responses = productUtil.mapToProductResponses(products);
        return new SuccessDataResult<>(responses, "All Product fetched");

    }

    @Override
    public Result save(ProductCreateRequest request) {

        Product product = Product.builder()
                .productName(request.getProductName())
                .productDescription(request.getProductDescription())
                .price(request.getPrice())
                .quantity(0)
                .transaction(new ArrayList<>())
                .build();

        return save(product);

    }

    @Override
    public Result save(Product product) {

        try {
            productRepository.save(product);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e);
        }
        return new SuccessResult("Product saved");
    }

    @Override
    public Result updateProduct(UUID productId, ProductUpdateRequest request) {

        DataResult<Product> productResult = getProductById(productId);
        if (!productResult.isSuccess()) {
            return new ErrorResult(productResult.getMessage());
        }
        Product product = productResult.getData();

        product.setProductName(request.getProductName());
        product.setProductDescription(request.getProductDescription());
        product.setPrice(request.getPrice());
        product.setPrice(request.getPrice());

        Result saveResult = save(product);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }

        return new SuccessResult("Product updated: " + productId);

    }

    @Override
    public Result deleteProduct(UUID productId) {
        DataResult<Product> productResult = getProductById(productId);
        if (!productResult.isSuccess()) {
            return new ErrorResult(productResult.getMessage());
        }
        Product product = productResult.getData();

        productRepository.delete(product);

        return new SuccessResult("Product deleted: " + productId);
    }
}
