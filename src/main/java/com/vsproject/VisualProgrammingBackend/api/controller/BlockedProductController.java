package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.BlockedProductResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.service.abstracts.BlockedProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/blocked-product")
@RequiredArgsConstructor
public class BlockedProductController {

    private final BlockedProductService blockedProductService;

    @GetMapping
    public ResponseEntity<DataResult<List<BlockedProductResponse>>> getAllBlockedByParent(@RequestParam int page, @RequestParam int size) {

        DataResult<List<BlockedProductResponse>> result = blockedProductService.getAllBlockedByParentResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping
    public ResponseEntity<Result> blockProductForStudent(@RequestBody BlockedProductRequest request) {

        Result result = blockedProductService.blockProduct(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @DeleteMapping("/{blocked-id}")
    public ResponseEntity<Result> deleteBlockedProduct(@PathVariable(name = "blocked-id") UUID blockedId) {

        Result result = blockedProductService.delete(blockedId);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
