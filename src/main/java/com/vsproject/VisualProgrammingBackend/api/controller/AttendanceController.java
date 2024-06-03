package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.AttendanceCheckRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentAttendanceResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.service.abstracts.AttendanceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/attendance")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @GetMapping
    public ResponseEntity<DataResult<List<StudentAttendanceResponse>>> getAllAttendanceResponse(@RequestParam int page, @RequestParam int size) {

        DataResult<List<StudentAttendanceResponse>> result = attendanceService.getAllAttendanceResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("/take-attendance")
    public ResponseEntity<Result> takeAttendance(@RequestBody AttendanceCheckRequest request) {

        Result result = attendanceService.takeAttendance(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }



}
