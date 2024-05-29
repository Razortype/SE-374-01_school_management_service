package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.StudentCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentUpgradeRequest;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.core.utils.StudentUtil;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import com.vsproject.VisualProgrammingBackend.entity.User;
import com.vsproject.VisualProgrammingBackend.repository.StudentRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final AuthUserUtil authUserUtil;
    private final PasswordEncoder passwordEncoder;

    private final StudentUtil studentUtil;

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
                .password(user.getPassword())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .phoneNumber(user.getPhoneNumber())
                .role(Role.STUDENT)
                .birthYear(user.getBirthYear())
                .createdAt(user.getCreatedAt())

                .schoolNumber(request.getSchoolNumber())
                .upgradedAt(LocalDateTime.now())
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

    @Override
    public Result register(StudentCreateRequest request) {

        LocalDateTime now = LocalDateTime.now();

        Student student = Student.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .schoolNumber(request.getSchoolNumber())
                .phoneNumber(request.getPhoneNumber())
                .birthYear(request.getBirthYear())
                .role(Role.STUDENT)
                .createdAt(now)
                .upgradedAt(now)
                .build();

        return save(student);

    }

    @Override
    public DataResult<List<StudentResponse>> getAllStudents() {

        List<Student> students = studentRepository.findAll();
        List<StudentResponse> responseList = studentUtil.mapToStudentResponses(students);
        return new SuccessDataResult<>(responseList, "Students fetched");

    }

    @Override
    public List<Student> getStudentListByIdList(List<Integer> studentIds) {
        List<Student> students = studentRepository.findAllByIdIn(studentIds);
        return students;
    }

}
