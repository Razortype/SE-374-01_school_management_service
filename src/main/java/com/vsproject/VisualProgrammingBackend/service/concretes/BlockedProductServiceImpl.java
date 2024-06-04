package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductResponse;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.core.utils.BlockedProductUtil;
import com.vsproject.VisualProgrammingBackend.entity.*;
import com.vsproject.VisualProgrammingBackend.repository.BlockedProductRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.BlockedProductService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ParentService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.ProductService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlockedProductServiceImpl implements BlockedProductService {

    private final BlockedProductRepository blockedProductRepository;

    private final ProductService productService;
    private final StudentService studentService;
    private final ParentService parentService;

    private final BlockedProductUtil blockedProductUtil;

    private final AuthUserUtil authUserUtil;

    @Override
    public DataResult<List<BlockedProductResponse>> getAllBlockedByParentResponse(int page, int size) {

        User user = authUserUtil.getAuthenticatedUser();
        if (user.getRole() != Role.PARENT) {
            return new ErrorDataResult<>("Only parents can make this request. Get blocked by PARENT");
        }

        Page<StudentBlockedProduct> products = blockedProductRepository.findAllByParent((Parent) user, PageRequest.of(page, size));
        List<BlockedProductResponse> responses = blockedProductUtil.mapToBlockedProductResponses(products.toList());
        return new SuccessDataResult<>(responses, "All StudentBlockedProduct fetched by PARENT: " + user.getEmail());

    }

    @Override
    public DataResult<StudentBlockedProduct> getBlockedProductById(UUID id) {

        StudentBlockedProduct blockedProduct = blockedProductRepository.findById(id).orElse(null);
        if (blockedProduct == null) {
            return new ErrorDataResult<>("StudentBlockedProduct not found: " + id);
        }
        return new SuccessDataResult<>(blockedProduct, "StudentBlockedProduct found");

    }

    @Override
    public Result blockProduct(BlockedProductRequest request) {

        User user = authUserUtil.getAuthenticatedUser();
        if (user.getRole() != Role.PARENT) {
            return new ErrorDataResult<>("Only parents can make this request. block by PARENT");
        }
        DataResult<Product> productResult = productService.getProductById(request.getProductId());
        if (!productResult.isSuccess()) {
            return new ErrorResult(productResult.getMessage());
        }
        DataResult<Student> studentResult = studentService.getStudentById(request.getStudentId());
        if (!studentResult.isSuccess()) {
            return new SuccessResult(studentResult.getMessage());
        }

        StudentBlockedProduct blockedProduct = StudentBlockedProduct.builder()
                .product(productResult.getData())
                .student(studentResult.getData())
                .parent((Parent) user)
                .blockReason(request.getReason())
                .build();

        return save(blockedProduct);

    }

    @Override
    public Result delete(UUID blockedId) {

        DataResult<StudentBlockedProduct> blockedProductResult = getBlockedProductById(blockedId);
        if (!blockedProductResult.isSuccess()) {
            return new ErrorResult(blockedProductResult.getMessage());
        }
        blockedProductRepository.delete(blockedProductResult.getData());
        return new SuccessResult("StudentBlockedProduct deleted");

    }

    @Override
    public Result save(StudentBlockedProduct blockedProduct) {
        try {
            blockedProductRepository.save(blockedProduct);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("StudentBlockProduct saved");
    }

}
