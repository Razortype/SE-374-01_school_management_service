package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SchoolClassResponse {

    @JsonProperty("class_id")
    private UUID classId;

    @JsonProperty("class_name")
    private String className;

    @JsonProperty("class_code")
    private String classCode;

    @JsonProperty("course_sections")
    private List<CourseSectionResponse> courseSectionResponses;

    @JsonProperty("students")
    private List<StudentResponse> studentResponses;

}
