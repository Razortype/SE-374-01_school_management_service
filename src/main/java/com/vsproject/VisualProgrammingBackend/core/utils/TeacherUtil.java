package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.TeacherResponse;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherUtil {

    public List<TeacherResponse> convertTeacherResponses(List<Teacher> teachers) {

        return teachers.stream()
                .map(this::convertTeacherResponse)
                .toList();

    }

    public TeacherResponse convertTeacherResponse(Teacher teacher) {

        return TeacherResponse.builder()
                .teacherId(teacher.getId())
                .email(teacher.getEmail())
                .firstname(teacher.getFirstname())
                .lastname(teacher.getLastname())
                .phoneNumber(teacher.getPhoneNumber())
                .birthYear(teacher.getBirthYear())
                .role(teacher.getRole())
                .createdAt(teacher.getCreatedAt())
                .profession(teacher.getProfession())
                .upgradedAt(teacher.getUpgradedAt())
                .build();
    }

}
