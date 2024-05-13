package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.CourseCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionCreateRequest;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.entity.Course;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.repository.CourseRepository;
import com.vsproject.VisualProgrammingBackend.repository.CourseSectionRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CourseService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.SchoolClassService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseSectionRepository courseSectionRepository;

    private final TeacherService teacherService;
    private final SchoolClassService schoolClassService;

    @Override
    public Result createCourse(CourseCreateRequest request) {

        Course course = Course.builder()
                .courseTitle(request.getCourseTitle())
                .courseCode(request.getCourseCode())
                .courseSections(new ArrayList<>())
                .build();

        return save(course);

    }

    @Override
    public Result save(Course course) {
        try {
            courseRepository.save(course);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("Course saved");
    }

    @Override
    public DataResult<Course> getCourseById(UUID id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return new ErrorDataResult<>("Course not found:" + id);
        }
        return new SuccessDataResult<>("Course found");
    }

    @Override
    public Result registerToClass(SchoolClass schoolClass, CourseSectionCreateRequest request) {

        DataResult teacherResult = teacherService.getTeacherById(request.getTeacherId());
        if (!teacherResult.isSuccess()) {
            return new ErrorDataResult<>(teacherResult.getMessage());
        }
        DataResult courseResult = getCourseById(request.getCourseId());
        if (!courseResult.isSuccess()) {
            return new ErrorDataResult<>(courseResult.getMessage());
        }

        CourseSection courseSection = CourseSection.builder()
                .teacher((Teacher) teacherResult.getData())
                .course((Course) courseResult.getData())
                .schoolClass(schoolClass)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .build();

        return save(courseSection);

    }

    @Override
    public Result save(CourseSection courseSection) {
        try {
            courseSectionRepository.save(courseSection);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("CourseSection saved");
    }
}
