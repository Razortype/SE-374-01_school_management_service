package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionResponse;
import com.vsproject.VisualProgrammingBackend.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseUtil {

    public List<CourseResponse> convertCourseResponses(List<Course> courses) {

        return courses.stream()
                .map(this::convertCourseResponse)
                .toList();

    }

    public CourseResponse convertCourseResponse(Course course) {

        return CourseResponse.builder()
                .courseId(course.getId())
                .courseTitle(course.getCourseTitle())
                .courseCode(course.getCourseCode())
                .build();

    }

}
