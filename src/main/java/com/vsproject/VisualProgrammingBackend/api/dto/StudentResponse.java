package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponse {

    private int id;
    private String email;
    private String firstname;
    private String lastname;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("birth_year")
    private int birthYear;

    @JsonProperty("school_number")
    private String schoolNumber;

    @Column(name = "in_school")
    private boolean inSchool;

}
