package com.vsproject.VisualProgrammingBackend.core.utils;

import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SchoolClassUtil {

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
                .filter(id -> studentIds.contains(id))
                .toList();

    }

    private List<Integer> getRegisteredStudentIdList(SchoolClass schoolClass) {
        List<Integer> ids = schoolClass.getStudents().stream()
                .map(student -> student.getId())
                .toList();
        return ids;
    }

}
