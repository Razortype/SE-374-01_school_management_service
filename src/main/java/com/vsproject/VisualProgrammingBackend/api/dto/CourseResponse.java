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
public class CourseResponse {

    @JsonProperty("course_id")
    private UUID courseId;

    @JsonProperty("course_title")
    private String courseTitle;

    @JsonProperty("course_code")
    private String courseCode;

}
