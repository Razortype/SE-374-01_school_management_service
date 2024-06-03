package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.PurchaseRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TransactionResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    List<Transaction> getAllTransaction(LocalDateTime startDate, LocalDateTime endDate);
    List<Transaction> getAllTransaction(int page, int size, LocalDateTime startDate, LocalDateTime endDate);
    DataResult<List<TransactionResponse>> getAllTransactionResponse(int page, int size, LocalDateTime startDate, LocalDateTime endDate);
    Result save(Transaction transaction);
    Result purchase(PurchaseRequest request);
    Result depositMoney(int studentId, double amount);

}
