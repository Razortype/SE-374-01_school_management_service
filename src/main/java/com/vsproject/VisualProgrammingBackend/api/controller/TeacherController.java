package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.TeacherCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.TeacherResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<Result> createTeacher(@RequestBody TeacherCreateRequest request) {

        Result result = teacherService.createTeacher(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @GetMapping
    public ResponseEntity<DataResult<List<TeacherResponse>>> getAllTeacherResponse(@RequestParam int page, @RequestParam int size) {

        DataResult<List<TeacherResponse>> result = teacherService.getAllTeacherResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
