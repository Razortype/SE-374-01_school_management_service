package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductResponse;
import com.vsproject.VisualProgrammingBackend.entity.StudentBlockedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlockedProductUtil {

    public List<BlockedProductResponse> mapToBlockedProductResponses(List<StudentBlockedProduct> blockedProducts) {

        return blockedProducts.stream()
                .map(this::mapToBlockedProductResponse)
                .toList();

    }

    public BlockedProductResponse mapToBlockedProductResponse(StudentBlockedProduct blockedProduct) {

        return BlockedProductResponse.builder()
                .blockedId(blockedProduct.getId())
                .productId(blockedProduct.getProduct().getId())
                .studentId(blockedProduct.getStudent().getId())
                .parentId(blockedProduct.getParent().getId())
                .reason(blockedProduct.getBlockReason())
                .build();

    }

}
