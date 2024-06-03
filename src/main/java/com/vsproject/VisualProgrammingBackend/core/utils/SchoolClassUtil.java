package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.SchoolClassResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchoolClassUtil {

    private final CourseSectionUtil courseSectionUtil;
    private final StudentUtil studentUtil;

    public List<Integer> identifyNotExistStudents(SchoolClass schoolClass, List<Integer> studentIds) {

        List<Integer> registeredIds = getRegisteredStudentIdList(schoolClass);
        if (registeredIds.isEmpty()) { return new ArrayList<>(); }

        return studentIds.stream()
                .filter(id -> !registeredIds.contains(id))
                .toList();

    }

    public List<Integer> identifyExistStudents(SchoolClass schoolClass, List<Integer> studentIds) {

        List<Integer> registeredIds = getRegisteredStudentIdList(schoolClass);
        if (registeredIds.isEmpty()) { return new ArrayList<>(); }

        return registeredIds.stream()
                .filter(studentIds::contains)
                .toList();

    }

    private List<Integer> getRegisteredStudentIdList(SchoolClass schoolClass) {

        List<Integer> ids = schoolClass.getStudents().stream()
                .map(student -> student.getId())
                .toList();
        return ids;

    }

    public List<SchoolClassResponse> convertSchoolClassResponses(List<SchoolClass> classes) {

        return classes.stream()
                .map(this::convertSchoolClassResponse)
                .toList();

    }

    public SchoolClassResponse convertSchoolClassResponse(SchoolClass schoolClass) {

        List<CourseSectionResponse> courseSectionResponses = courseSectionUtil.convertCourseSectionResponses(schoolClass.getCourseSections());
        List<StudentResponse> studentResponses = studentUtil.mapToStudentResponses(schoolClass.getStudents());

        return SchoolClassResponse.builder()
                .classId(schoolClass.getId())
                .className(schoolClass.getClassName())
                .courseSectionResponses(courseSectionResponses)
                .studentResponses(studentResponses)
                .build();

    }

}
