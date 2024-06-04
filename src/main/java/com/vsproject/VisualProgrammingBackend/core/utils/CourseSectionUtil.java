package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionResponse;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSectionUtil {

    public List<CourseSectionResponse> mapToCourseSectionResponses(List<CourseSection> courseSections) {

        return courseSections.stream()
                .map(this::mapToCourseSectionResponse)
                .toList();

    }

    public CourseSectionResponse mapToCourseSectionResponse(CourseSection courseSection) {
        return CourseSectionResponse.builder()
                .courseSectionId(courseSection.getId())
                .startTime(courseSection.getStartTime())
                .endTime(courseSection.getEndTime())
                .weekDay(courseSection.getWeekDay())
                .teacherId(courseSection.getTeacher().getId())
                .courseId(courseSection.getCourse().getId())
                .schoolClassId(courseSection.getSchoolClass().getId())
                .build();
    }

}
