package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockedProductRequest {

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("student_id")
    private int studentId;

    @JsonProperty("reason")
    private String reason;

}
