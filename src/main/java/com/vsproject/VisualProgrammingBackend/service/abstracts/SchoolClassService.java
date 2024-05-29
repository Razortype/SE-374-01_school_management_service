package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.ClassCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.SchoolClassResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;

import java.util.List;
import java.util.UUID;

public interface SchoolClassService {

    Result createClass(ClassCreateRequest request);
    Result createClass(SchoolClass schoolClass);
    Result save(SchoolClass schoolClass);
    DataResult<SchoolClass> getSchoolClassById(UUID id);

    Result addStudent(UUID schoolClassId, List<Integer> studentIds);
    Result removeStudents(UUID schoolClassId, List<Integer> studentIds);
    Result registerCourseSectionToClass(UUID schoolClassId, CourseSectionCreateRequest request);

    DataResult<List<SchoolClassResponse>> getAllSchoolClassResponse(int page, int size);

}
