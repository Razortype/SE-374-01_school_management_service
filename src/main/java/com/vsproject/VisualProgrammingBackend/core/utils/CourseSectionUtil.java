package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionResponse;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSectionUtil {

    public List<CourseSectionResponse> convertCourseSectionResponses(List<CourseSection> courseSections) {

        return courseSections.stream()
                .map(this::convertCourseSectionResponse)
                .toList();

    }

    public CourseSectionResponse convertCourseSectionResponse(CourseSection courseSection) {
        return CourseSectionResponse.builder()
                .courseSectionId(courseSection.getId())
                .startTime(courseSection.getStartTime())
                .endTime(courseSection.getEndTime())
                .teacherId(courseSection.getTeacher().getId())
                .courseId(courseSection.getCourse().getId())
                .schoolClassId(courseSection.getSchoolClass().getId())
                .build();
    }

}
