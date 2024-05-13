package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.StudentCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import com.vsproject.VisualProgrammingBackend.entity.User;

import java.util.List;

public interface StudentService {

    DataResult<Student> getStudentByEmail(String email);
    Result create(User user, StudentUpgradeRequest request);
    Result save(Student student);
    Result register(StudentCreateRequest request);

    DataResult<List<StudentResponse>> getAllStudents();

    List<Student> getStudentListByIdList(List<Integer> studentIds);

}
