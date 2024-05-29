package com.vsproject.VisualProgrammingBackend.service.abstracts;


import com.vsproject.VisualProgrammingBackend.api.dto.TeacherCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.entity.User;

import java.util.List;

public interface TeacherService {

    DataResult<Teacher> getTeacherByEmail(String email);
    Result create(User user, TeacherUpgradeRequest request);
    Result save(Teacher teacher);

    DataResult<Teacher> getTeacherById(int id);

    Result createTeacher(TeacherCreateRequest request);
    DataResult<List<TeacherResponse>> getAllTeacherResponse(int page, int size);

}
