package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentAttendanceResponse;
import com.vsproject.VisualProgrammingBackend.entity.StudentAttendance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentAttendanceUtil {

    private final CourseSectionUtil courseSectionUtil;

    public List<StudentAttendanceResponse> mapToAttendanceResponses(List<StudentAttendance> attendances) {

        return attendances.stream()
                .map(this::mapToAttendanceResponse)
                .toList();

    }

    public StudentAttendanceResponse mapToAttendanceResponse(StudentAttendance attendance) {

        CourseSectionResponse sectionResponse = courseSectionUtil.convertCourseSectionResponse(attendance.getCourseSection());

        return StudentAttendanceResponse.builder()
                .id(attendance.getId())
                .attendanceType(attendance.getAttendanceType())
                .excuseDescription(attendance.getExcuseDescription())
                .absent(attendance.isAbsent())
                .attendanceDate(attendance.getAttendanceDate())
                .studentId(attendance.getStudent().getId())
                .courseSection(sectionResponse)
                .build();

    }

}
