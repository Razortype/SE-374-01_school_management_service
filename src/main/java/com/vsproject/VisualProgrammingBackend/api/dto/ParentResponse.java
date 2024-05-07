package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParentResponse {
    private int id;
    private String email;
    private String firstname;
    private String lastname;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("birth_year")
    private int birthYear;

    @JsonProperty("billing_address")
    private String billingAddress;

    private List<StudentResponse> students;
}
