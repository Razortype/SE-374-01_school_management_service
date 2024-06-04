package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.DepositRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.PurchaseRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TransactionResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/transaction")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<DataResult<List<TransactionResponse>>> getAllTransactionResponsePaged(@RequestParam(defaultValue = "-1") int page,
                                                                                                @RequestParam(defaultValue = "-1") int size,
                                                                                                @RequestParam(required = false) LocalDateTime startDate,
                                                                                                @RequestParam(required = false) LocalDateTime endDate) {

        DataResult<List<TransactionResponse>> result = transactionService.getAllTransactionResponse(page, size, startDate, endDate);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }


    @PostMapping("/purchase")
    public ResponseEntity<Result> purchase(@RequestBody PurchaseRequest request) {

        Result result = transactionService.purchase(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("/deposit")
    public ResponseEntity<Result> depositMoney(@RequestBody DepositRequest request) {

        Result result = transactionService.depositMoney(request.getStudentId(),request.getAmount());
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
