package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.WeekDay;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseSectionResponse {

    @JsonProperty("course_section_id")
    private UUID courseSectionId;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("end_time")
    private LocalTime endTime;

    @JsonProperty("week_day")
    private WeekDay weekDay;

    @JsonProperty("teacher_id")
    private int teacherId;

    @JsonProperty("course_id")
    private UUID courseId;

    @JsonProperty("school_class_id")
    private UUID schoolClassId;

}
