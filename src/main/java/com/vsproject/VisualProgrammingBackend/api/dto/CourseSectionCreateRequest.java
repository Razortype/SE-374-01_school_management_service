package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.WeekDay;
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
public class CourseSectionCreateRequest {

    @JsonProperty("teacher_id")
    private int teacherId;

    @JsonProperty("course_id")
    private UUID courseId;

    @JsonProperty("start_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @JsonProperty("end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @JsonProperty("week_day")
    private WeekDay weekDay;

}
