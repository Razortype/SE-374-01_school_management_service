package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.TransactionResponse;
import com.vsproject.VisualProgrammingBackend.entity.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionUtil {

    public List<TransactionResponse> mapToTransactionResponses(List<Transaction> transactions) {

        return transactions.stream()
                .map(this::mapToTransactionResponse)
                .toList();

    }

    public TransactionResponse mapToTransactionResponse(Transaction transaction) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .studentId(transaction.getStudent().getId())
                .productId(transaction.getProduct().getId())
                .quantityBought(transaction.getQuantityBought())
                .totalPrice(transaction.getTotalPrice())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }

}
