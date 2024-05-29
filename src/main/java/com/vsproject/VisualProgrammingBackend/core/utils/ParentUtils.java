package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.ParentResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.entity.Parent;
import com.vsproject.VisualProgrammingBackend.entity.StudentParentRelation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentUtils {

    private final StudentUtil studentUtil;

    public ParentUtils(StudentUtil studentUtil) {
        this.studentUtil = studentUtil;
    }

    public List<ParentResponse> mapToParentResponses(List<Parent> parents) {
        return parents.stream()
                .map(this::mapToParentResponse)
                .collect(Collectors.toList());
    }

    private ParentResponse mapToParentResponse(Parent parent) {
        List<StudentResponse> studentResponses = studentUtil.mapToStudentResponses(
                parent.getStudentRelations().stream()
                        .map(StudentParentRelation::getStudent)
                        .collect(Collectors.toList())
        );

        return ParentResponse.builder()
                .id(parent.getId())
                .email(parent.getEmail())
                .firstname(parent.getFirstname())
                .lastname(parent.getLastname())
                .phoneNumber(parent.getPhoneNumber())
                .birthYear(parent.getBirthYear())
                .billingAddress(parent.getBillingAddress())
                .students(studentResponses)
                .build();
    }
}