package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.StudentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.StudentRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AuthUserUtil authUserUtil;

    @Override
    public DataResult<Student> getStudentByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email).orElse(null);
        if (student == null) {
            return new ErrorDataResult<>("Student not found: " + email);
        }
        return new SuccessDataResult<>(student, "Student found");
    }

    @Override
    public Result create(User user, StudentUpgradeRequest request) {

        authUserUtil.demolishUser(user);

        Student student = Student.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .role(Role.STUDENT)
                .birthYear(user.getBirthYear())

                .schoolNumber(request.getSchoolNumber())
                .build();

        return save(student);

    }

    @Override
    public Result save(Student student) {

        try {
            studentRepository.save(student);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("Student saved");

    }

}
