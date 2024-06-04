package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.TeacherCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.auth.AuthenticationService;
import com.vsproject.VisualProgrammingBackend.config.JwtService;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.core.utils.TeacherUtil;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.TeacherRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final AuthUserUtil authUserUtil;
    private final TeacherUtil teacherUtil;
    private final PasswordEncoder passwordEncoder;

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
    public Result register(TeacherCreateRequest request) {

        LocalDateTime now = LocalDateTime.now();

        Teacher teacher = Teacher.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .profession(request.getProfession())
                .courseSections(new ArrayList<>())
                .phoneNumber(request.getPhoneNumber())
                .birthYear(request.getBirthYear())
                .role(Role.TEACHER)
                .createdAt(now)
                .upgradedAt(now)
                .build();

        return save(teacher);

    }

    @Override
    public DataResult<Teacher> getTeacherById(int id) {
        Teacher teacher = teacherRepository.findById(id).orElse(null);
        if (teacher == null) {
            return new ErrorDataResult<>("Teacher not found: " + id);
        }
        return new SuccessDataResult<>(teacher, "Teacher found");
    }

    @Override
    public Result createTeacher(TeacherCreateRequest request) {

        Teacher teacher = Teacher.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .phoneNumber(request.getPhoneNumber())
                .birthYear(request.getBirthYear())
                .role(Role.TEACHER)
                .createdAt(LocalDateTime.now())
                .profession(request.getProfession())
                .upgradedAt(LocalDateTime.now())
                .courseSections(new ArrayList<>())
                .build();

        Result saveResult = save(teacher);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }

        return new SuccessResult("Teacher saved");

    }

    @Override
    public DataResult<List<TeacherResponse>> getAllTeacherResponse(int page, int size) {

        Page<Teacher> teacherPage = teacherRepository.findAll(PageRequest.of(page, size));
        List<TeacherResponse> responses = teacherUtil.convertTeacherResponses(teacherPage.toList());

        return new SuccessDataResult<>(responses, "All Teacher fetched");

    }

}
