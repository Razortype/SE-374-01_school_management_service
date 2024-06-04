package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.*;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Course;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;

import java.util.List;
import java.util.UUID;

public interface CourseService {

    Result createCourse(CourseCreateRequest request);
    Result save(Course course);

    DataResult<Course> getCourseById(UUID id);
    Result save(CourseSection courseSection);

    DataResult<List<CourseResponse>> getAllCourseResponse(int page, int size);
    Result editCourse(UUID courseId, CourseEditRequest request);
    Result delete(CourseSection section);


    // course section
    DataResult<CourseSection> getCourseSectionById(UUID id);
    DataResult<List<CourseSectionResponse>> getAllCourseSectionResponse(int page, int size);
    Result registerSectionToClass(SchoolClass schoolClass, CourseSectionCreateRequest request);
    Result editCourseSection(UUID sectionId, CourseSectionEditRequest request);

}

