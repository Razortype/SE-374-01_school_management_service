package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.StudentCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    private final StudentService studentService;

    @GetMapping("/student")
    public ResponseEntity<DataResult<List<StudentResponse>>> getAllStudents() {

        DataResult<List<StudentResponse>> result = studentService.getAllStudents();
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

}
