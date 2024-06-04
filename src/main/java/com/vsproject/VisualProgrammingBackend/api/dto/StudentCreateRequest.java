package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {

    private String email;
    private String password;
    private String firstname;
    private String lastname;

    @JsonProperty("school_number")
    private String schoolNumber;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("birth_year")
    private int birthYear;

}
