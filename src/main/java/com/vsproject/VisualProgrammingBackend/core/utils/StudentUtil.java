package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentUtil {

    public List<StudentResponse> mapToStudentResponses(List<Student> students) {
        return students.stream()
                .map(this::mapToStudentResponse)
                .collect(Collectors.toList());
    }

    private StudentResponse mapToStudentResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .email(student.getEmail())
                .firstname(student.getFirstname())
                .lastname(student.getLastname())
                .phoneNumber(student.getPhoneNumber())
                .birthYear(student.getBirthYear())
                .schoolNumber(student.getSchoolNumber())
                .inSchool(student.isInSchool())
                .build();
    }
}
