package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.TeacherUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.TeacherRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final AuthUserUtil authUserUtil;

    @Override
    public DataResult<Teacher> getTeacherByEmail(String email) {

        Teacher teacher = teacherRepository.findTeacherByEmail(email).orElse(null);
        if (teacher == null) {
            return new ErrorDataResult<>("Teacher not found: " + email);
        }
        return new SuccessDataResult<>(teacher, "Teacher found");

    }

    @Override
    public Result create(User user, TeacherUpgradeRequest request) {

        authUserUtil.demolishUser(user);

        Teacher teacher = Teacher.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .role(Role.TEACHER)
                .birthYear(user.getBirthYear())
                .createdAt(user.getCreatedAt())

                .profession(request.getProfession())
                .upgradedAt(LocalDateTime.now())
                .build();

        return save(teacher);

    }

    @Override
    public Result save(Teacher teacher) {
        try {
            teacherRepository.save(teacher);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("Teacher saved");
    }

    @Override
    public DataResult<Teacher> getTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            return new ErrorDataResult<>("Teacher not found: " + id);
        }
        return new SuccessDataResult<>(teacher, "Teacher found");
    }
}
