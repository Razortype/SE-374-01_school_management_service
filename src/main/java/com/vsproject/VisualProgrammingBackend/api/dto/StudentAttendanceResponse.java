package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.AttendanceType;
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
public class StudentAttendanceResponse {

    @JsonProperty("attendance_id")
    private UUID id;

    @JsonProperty("attendance_type")
    private AttendanceType attendanceType;

    @JsonProperty("excuse_description")
    private String excuseDescription;

    private boolean absent;

    @JsonProperty("attendance_date")
    private LocalDateTime attendanceDate;

    @JsonProperty("student_id")
    private int studentId;

    @JsonProperty("course_section")
    private CourseSectionResponse courseSection;

}
