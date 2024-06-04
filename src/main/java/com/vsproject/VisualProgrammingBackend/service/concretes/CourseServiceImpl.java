package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.*;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.CourseSectionUtil;
import com.vsproject.VisualProgrammingBackend.core.utils.CourseUtil;
import com.vsproject.VisualProgrammingBackend.entity.Course;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;
import com.vsproject.VisualProgrammingBackend.entity.Teacher;
import com.vsproject.VisualProgrammingBackend.repository.CourseRepository;
import com.vsproject.VisualProgrammingBackend.repository.CourseSectionRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CourseService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseSectionRepository courseSectionRepository;

    private final TeacherService teacherService;

    private final CourseUtil courseUtil;
    private final CourseSectionUtil courseSectionUtil;

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
        return new SuccessDataResult<>(course, "Course found");
    }

    @Override
    public Result registerSectionToClass(SchoolClass schoolClass, CourseSectionCreateRequest request) {

        DataResult<Teacher> teacherResult = teacherService.getTeacherById(request.getTeacherId());
        if (!teacherResult.isSuccess()) {
            return new ErrorDataResult<>(teacherResult.getMessage());
        }
        DataResult<Course> courseResult = getCourseById(request.getCourseId());
        if (!courseResult.isSuccess()) {
            return new ErrorDataResult<>(courseResult.getMessage());
        }

        CourseSection courseSection = CourseSection.builder()
                .teacher(teacherResult.getData())
                .course(courseResult.getData())
                .schoolClass(schoolClass)
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .weekDay(request.getWeekDay())
                .build();

        return save(courseSection);

    }

    @Override
    public DataResult<List<CourseResponse>> getAllCourseResponse(int page, int size) {

        Page<Course> courses = courseRepository.findAll(PageRequest.of(page, size));
        List<CourseResponse> responses = courseUtil.convertCourseResponses(courses.toList());

        return new SuccessDataResult(responses, "All Course fetched");

    }

    @Override
    public Result editCourse(UUID courseId, CourseEditRequest request) {

        DataResult<Course> courseResult = getCourseById(courseId);
        if (!courseResult.isSuccess()) {
            return new ErrorResult(courseResult.getMessage());
        }
        Course course = courseResult.getData();

        course.setCourseTitle(request.getCourseTitle());
        course.setCourseCode(request.getCourseCode());

        Result saveResult = save(course);
        if(!saveResult.isSuccess()) {
            return saveResult;
        }

        return new SuccessResult("Course edited");

    }

    @Override
    public Result delete(CourseSection section) {
        try {
            courseSectionRepository.delete(section);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("CourseSection deleted");
    }

    @Override
    public DataResult<CourseSection> getCourseSectionById(UUID id) {

        CourseSection section = courseSectionRepository.findById(id).orElse(null);
        if (section == null) {
            return new ErrorDataResult<>("CourseSection not found: " + id);
        }

        return new SuccessDataResult<>(section, "CourseSection found");

    }

    // course section

    @Override
    public Result save(CourseSection courseSection) {
        try {
            courseSectionRepository.save(courseSection);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("CourseSection saved");
    }



    @Override
    public DataResult<List<CourseSectionResponse>> getAllCourseSectionResponse(int page, int size) {

        Page<CourseSection> courseSections = courseSectionRepository.findAll(PageRequest.of(page, size));
        List<CourseSectionResponse> responses = courseSectionUtil.mapToCourseSectionResponses(courseSections.toList());

        return new SuccessDataResult<>(responses, "All CourseSection fetched");

    }

    @Override
    public Result editCourseSection(UUID sectionId, CourseSectionEditRequest request) {

        DataResult<CourseSection> sectionResult = getCourseSectionById(sectionId);
        if (sectionResult.isSuccess()) {
            return new ErrorResult(sectionResult.getMessage());
        }
        CourseSection section = sectionResult.getData();

        section.setStartTime(request.getStartTime());
        section.setEndTime(request.getEndTime());
        section.setWeekDay(request.getWeekDay());

        Result saveResult = save(section);
        if (!saveResult.isSuccess()) {
            return saveResult;
        }

        return new SuccessResult("CourseSection edited");

    }


}
