package com.vsproject.VisualProgrammingBackend.api.controller;

import com.vsproject.VisualProgrammingBackend.api.dto.*;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CourseService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/course")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Result> createCourse(@RequestBody CourseCreateRequest request) {

        Result result = courseService.createCourse(request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @GetMapping
    public ResponseEntity<DataResult<List<CourseResponse>>> getAllCourses(@RequestParam int page, @RequestParam int size) {

        DataResult<List<CourseResponse>> result = courseService.getAllCourseResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("/{course-id}")
    public ResponseEntity<Result> editCourseById(@PathVariable(name = "course-id") UUID courseId,
                                                 @RequestBody CourseEditRequest request) {

        Result result = courseService.editCourse(courseId, request);
        result.determineHttpStatus();

        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }


    // course section

    @GetMapping("/section")
    public ResponseEntity<DataResult<List<CourseSectionResponse>>> getAllCourseSection(@RequestParam int page, @RequestParam int size) {

        DataResult<List<CourseSectionResponse>> result = courseService.getAllCourseSectionResponse(page, size);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

    @PutMapping("/section/{section-id}")
    public ResponseEntity<Result> editCourseSectionById(@PathVariable(name = "section-id") UUID sectionId,
                                                        @RequestBody CourseSectionEditRequest request) {

        Result result = courseService.editCourseSection(sectionId, request);
        result.determineHttpStatus();
        return ResponseEntity.status(result.getHttpStatus())
                .body(result);

    }

}
