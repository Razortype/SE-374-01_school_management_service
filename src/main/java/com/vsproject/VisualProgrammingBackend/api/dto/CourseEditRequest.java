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
public class CourseEditRequest {

    @JsonProperty("course_title")
    private String courseTitle;

    @JsonProperty("course_code")
    private String courseCode;

}
