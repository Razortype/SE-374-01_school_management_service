package com.vsproject.VisualProgrammingBackend.api.dto;

import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.Profession;
import jakarta.persistence.Column;
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
public class TeacherResponse {

    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "email")
    private String email;

    private String firstname;
    private String lastname;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_year")
    private int birthYear;

    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Profession profession;

    @Column(name = "upgraded_at")
    private LocalDateTime upgradedAt;

}
