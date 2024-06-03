package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.ClassCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.SchoolClassResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;
import com.vsproject.VisualProgrammingBackend.service.abstracts.SchoolClassService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/class")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@SecurityRequirement(name = "Bearer Authentication")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    @GetMapping
    public ResponseEntity<DataResult<List<SchoolClassResponse>>> getAllSchoolClass(@RequestParam int page, @RequestParam int size) {

        DataResult<List<SchoolClassResponse>> result = schoolClassService.getAllSchoolClassResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PostMapping
    public ResponseEntity<Result> createSchoolClass(@RequestBody ClassCreateRequest request) {

        Result result = schoolClassService.createClass(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("{class-id}/student/add")
    public ResponseEntity<Result> addStudentToClass(
            @PathVariable(name = "class-id") UUID schoolClassId,
            @RequestBody List<Integer> studentIds) {

        Result result = schoolClassService.addStudent(schoolClassId, studentIds);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("{class-id}/student/remove")
    public ResponseEntity<Result> removeStudentFromClass(
            @PathVariable(name = "class-id") UUID schoolClassId,
            @RequestBody List<Integer> studentIds) {

        Result result = schoolClassService.removeStudents(schoolClassId, studentIds);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
