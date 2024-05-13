package com.vsproject.VisualProgrammingBackend.service.abstracts;


import com.vsproject.VisualProgrammingBackend.api.dto.TeacherUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.entity.User;

public interface TeacherService {

    DataResult<Teacher> getTeacherByEmail(String email);
    Result create(User user, TeacherUpgradeRequest request);
    Result save(Teacher teacher);

    DataResult<Teacher> getTeacherById(int id);

}
