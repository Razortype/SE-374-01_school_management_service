package com.vsproject.VisualProgrammingBackend.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceCheckRequest {

    @JsonProperty("course_section_id")
    private UUID courseSectionId;

    @JsonProperty("list_of_student_absent")
    private List<Integer> studentAbsentList;

    @JsonProperty("attendance_taken_date")
    private LocalDateTime attendanceTakenDate;

    @JsonProperty("present_sentence")
    private String presentSentence;

    @JsonProperty("absent_sentence")
    private String absentSentence;

}
