package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.ClassCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.CourseSectionCreateRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.SchoolClassResponse;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.SchoolClassUtil;
import com.vsproject.VisualProgrammingBackend.entity.CourseSection;
import com.vsproject.VisualProgrammingBackend.entity.SchoolClass;
import com.vsproject.VisualProgrammingBackend.entity.Student;
import com.vsproject.VisualProgrammingBackend.repository.SchoolClassRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CourseService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.SchoolClassService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.StudentService;
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
public class SchoolClassServiceImpl implements SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final CourseService courseService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    private final SchoolClassUtil schoolClassUtil;

    @Override
    public Result createClass(ClassCreateRequest request) {
        SchoolClass newSchoolClass = SchoolClass.builder()
                .className(request.getClassName())
                .classCode(request.getClassCode())
                .courseSections(new ArrayList<>())
                .students(new ArrayList<>())
                .build();

        return createClass(newSchoolClass);
    }

    @Override
    public Result createClass(SchoolClass schoolClass) {
        return save(schoolClass);
    }

    @Override
    public Result save(SchoolClass schoolClass) {
        try {
            schoolClassRepository.save(schoolClass);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("SchoolClass saved");
    }

    @Override
    public DataResult<SchoolClass> getSchoolClassById(UUID id) {
        SchoolClass schoolClass = schoolClassRepository.findById(id).orElse(null);
        if (schoolClass == null) {
            return new ErrorDataResult<>("SchoolClass not found: " + id);
        }
        return new SuccessDataResult<>(schoolClass, "SchoolClass found");
    }

    @Override
    public Result addStudent(UUID schoolClassId, List<Integer> studentIds) {
        DataResult<SchoolClass> classResult = getSchoolClassById(schoolClassId);
        if (!classResult.isSuccess()) {
            return new ErrorDataResult<>(classResult.getMessage());
        }
        SchoolClass schoolClass = classResult.getData();

        List<Integer> nonExistIds = schoolClassUtil.identifyNotExistStudents(schoolClass, studentIds);
        List<Student> students = studentService.getStudentListByIdList(nonExistIds);

        for (Student student : students) {
            schoolClass.getStudents().add(student);
            student.setSchoolClass(schoolClass);

            Result studentSaveResult = studentService.save(student);
            if (!studentSaveResult.isSuccess()) {
                return studentSaveResult;
            }

        }
        Result schoolSaveResult = save(schoolClass);
        if (!schoolSaveResult.isSuccess()) {
            return schoolSaveResult;
        }

        return new SuccessResult("Student List added to SchoolClass");
    }

    @Override
    public Result removeStudents(UUID schoolClassId, List<Integer> studentIds) {
        DataResult<SchoolClass> classResult = getSchoolClassById(schoolClassId);
        if (!classResult.isSuccess()) {
            return new ErrorDataResult<>(classResult.getMessage());
        }
        SchoolClass schoolClass = classResult.getData();

        List<Integer> existIds = schoolClassUtil.identifyExistStudents(schoolClass, studentIds);
        List<Student> students = studentService.getStudentListByIdList(existIds);

        for (Student student : students) {
            schoolClass.getStudents().remove(student);
            student.setSchoolClass(null);

            studentService.save(student);
        }
        save(schoolClass);

        return new SuccessResult("Student List removed from SchoolClass");
    }

    @Override
    public Result registerCourseSectionToClass(UUID schoolClassId, CourseSectionCreateRequest request) {
        DataResult<SchoolClass> schoolClassResult = getSchoolClassById(schoolClassId);
        if (!schoolClassResult.isSuccess()) {
            return new ErrorDataResult<>(schoolClassResult.getMessage());
        }

        return courseService.registerSectionToClass(schoolClassResult.getData(), request);
    }

    @Override
    public Result removeCourseSectionToClass(UUID schoolClassId, UUID courseSectionId) {

        DataResult<SchoolClass> schoolClassResult = getSchoolClassById(schoolClassId);
        if (!schoolClassResult.isSuccess()) {
            return new ErrorResult(schoolClassResult.getMessage());
        }
        SchoolClass schoolClass = schoolClassResult.getData();

        DataResult<CourseSection> courseSectionResult = courseService.getCourseSectionById(courseSectionId);
        if (!courseSectionResult.isSuccess()) {
            return new ErrorResult(courseSectionResult.getMessage());
        }
        CourseSection section = courseSectionResult.getData();

        if (!schoolClass.getCourseSections().contains(section)) {
            return new ErrorResult("Class not contain section");
        }

        Result deleteResult = courseService.delete(section);
        if (!deleteResult.isSuccess()) {
            return deleteResult;
        }

        return new SuccessResult("Section removed from class");

    }

    @Override
    public DataResult<List<SchoolClassResponse>> getAllSchoolClassResponse(int page, int size) {

        if (page < 0 || size <= 0) {
            return new ErrorDataResult<>("Page or Size value error. page >= 0, size > 0");
        }

        Page<SchoolClass> classes = schoolClassRepository.findAll(PageRequest.of(page, size));
        List<SchoolClassResponse> responses = schoolClassUtil.convertSchoolClassResponses(classes.toList());

        return new SuccessDataResult<>(responses, "All SchoolClass fetched");

    }

}
