package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    @JsonProperty("transaction_id")
    private UUID id;

    @JsonProperty("student_id")
    private int studentId;

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("quantity_bought")
    private int quantityBought;

    @JsonProperty("total_price")
    private double totalPrice;

    @JsonProperty("transaction_date")
    private LocalDateTime transactionDate;

}
