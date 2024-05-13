package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionCreateRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Course;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;

import java.util.UUID;

public interface CourseService {

    DataResult<Course> getCourseById(UUID id);
    Result registerToClass(SchoolClass schoolClass, CourseSectionCreateRequest request);
    Result save(CourseSection courseSection);

}

