package com.vsproject.VisualProgrammingBackend.service.concretes;

import com.vsproject.VisualProgrammingBackend.api.dto.AttendanceCheckRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentAttendanceResponse;
import com.vsproject.VisualProgrammingBackend.core.enums.Role;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.AttendanceType;
import com.vsproject.VisualProgrammingBackend.core.results.*;
import com.vsproject.VisualProgrammingBackend.core.utils.AuthUserUtil;
import com.vsproject.VisualProgrammingBackend.core.utils.SchoolClassUtil;
import com.vsproject.VisualProgrammingBackend.core.utils.StudentAttendanceUtil;
import com.vsproject.VisualProgrammingBackend.entity.*;
import com.vsproject.VisualProgrammingBackend.repository.StudentAttendanceRepository;
import com.vsproject.VisualProgrammingBackend.service.abstracts.AttendanceService;
import com.vsproject.VisualProgrammingBackend.service.abstracts.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final StudentAttendanceUtil attendanceUtil;
    private final AuthUserUtil authUserUtil;
    private final CourseService courseService;
    private final SchoolClassUtil schoolClassUtil;
    private final StudentAttendanceRepository studentAttendanceRepository;

    @Override
    public List<StudentAttendance> getAllAttendance(int page, int size) {

        Page<StudentAttendance> attendances = studentAttendanceRepository.findAll(PageRequest.of(page, size));
        return attendances.toList();

    }

    @Override
    public DataResult<List<StudentAttendanceResponse>> getAllAttendanceResponse(int page, int size) {

        List<StudentAttendance> attendances = getAllAttendance(page, size);
        List<StudentAttendanceResponse> responses = attendanceUtil.mapToAttendanceResponses(attendances);
        return new SuccessDataResult<>(responses, "All StudentAttendance fetched");

    }

    @Override
    public Result save(StudentAttendance attendance) {

        try {
            studentAttendanceRepository.save(attendance);
        } catch (Exception e) {
            return new ErrorResult("UEO: " + e.getMessage());
        }
        return new SuccessResult("StudentAttendance saved");

    }

    @Override
    public Result takeAttendance(AttendanceCheckRequest request) {

        User user = authUserUtil.getAuthenticatedUser();
        if (user == null) {
            return new ErrorResult("Auth user not found");
        }

        DataResult<CourseSection> sectionResult = courseService.getCourseSectionById(request.getCourseSectionId());
        if (!sectionResult.isSuccess()) {
            return new ErrorResult(sectionResult.getMessage());
        }
        CourseSection section = sectionResult.getData();
        SchoolClass schoolClass = section.getSchoolClass();
        Teacher teacher = section.getTeacher();

        if (teacher.getId() == user.getId()) {
            return new ErrorResult(String.format("User is not responsible to take attendance. req:%s, given:%s", teacher.getEmail(), user.getEmail()));
        }

        List<StudentAttendance> attendanceList = new ArrayList<>();
        List<Integer> absentStudent = schoolClassUtil.identifyExistStudents(schoolClass, request.getStudentAbsentList());
        for (Student student: schoolClass.getStudents()) {

            boolean isAbsent = absentStudent.contains(student.getId());
            AttendanceType attendanceType = isAbsent ? AttendanceType.NOT_ATTENDANT : AttendanceType.ATTENDANT;
            String attendanceSentence = isAbsent ? request.getAbsentSentence() : request.getPresentSentence();

            attendanceList.add(StudentAttendance.builder()
                                .attendanceType(attendanceType)
                                .excuseDescription(attendanceSentence)
                                .absent(isAbsent)
                                .attendanceDate(request.getAttendanceTakenDate())
                                .student(student)
                                .courseSection(section)
                                .build());
        }

        for (StudentAttendance attendance: attendanceList) {
            save(attendance);
        }

        return new SuccessResult("Attendance checked");
    }

}
