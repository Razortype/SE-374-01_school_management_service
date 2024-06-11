package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.StudentCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherResponse;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.AccountType;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    private final UserService userService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    @GetMapping("/student")
    public ResponseEntity<DataResult<List<StudentResponse>>> getAllStudents(@RequestParam int page, @RequestParam int size) {

        DataResult<List<StudentResponse>> result = studentService.getAllStudents(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping("/create/student")
    public ResponseEntity<Result> createStudent(@RequestBody StudentCreateRequest request) {

        Result result = studentService.register(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @GetMapping("/teacher")
    public ResponseEntity<DataResult<List<TeacherResponse>>> getAllTeacher(@RequestParam int page, @RequestParam int size) {

        DataResult<List<TeacherResponse>> result = teacherService.getAllTeacherResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping("/create/teacher")
    public ResponseEntity<Result> createTeacher(@RequestBody TeacherCreateRequest request) {

        Result result = teacherService.register(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping("/admin/upgrade")
    public ResponseEntity<Result> upgradeUser(
            @RequestParam(name = "user_id") int userId,
            @RequestParam(name = "account_type") AccountType accountType,
            @RequestBody Map<String, Object> upgradeRequest
    ) {

        Result upgradeResult = userService.upgradeAdminUserAccount(userId, accountType, upgradeRequest);
        upgradeResult.determineHttpStatus();

        return ResponseEntity.status(upgradeResult.getHttpStatus())
                .body(upgradeResult);

    }

}
