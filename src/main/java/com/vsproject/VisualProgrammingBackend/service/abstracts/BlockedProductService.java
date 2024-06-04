package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.StudentBlockedProduct;

import java.util.List;
import java.util.UUID;

public interface BlockedProductService {

    DataResult<List<BlockedProductResponse>> getAllBlockedByParentResponse(int page, int size);
    DataResult<StudentBlockedProduct> getBlockedProductById(UUID id);
    Result blockProduct(BlockedProductRequest request);
    Result delete(UUID blockedId);
    Result save(StudentBlockedProduct blockedProduct);

}
