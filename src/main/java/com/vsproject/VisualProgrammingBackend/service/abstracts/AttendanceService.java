package com.vsproject.VisualProgrammingBackend.service.abstracts;

import com.vsproject.VisualProgrammingBackend.api.dto.AttendanceCheckRequest;
import com.vsproject.VisualProgrammingBackend.api.dto.StudentAttendanceResponse;
import com.vsproject.VisualProgrammingBackend.core.results.DataResult;
import com.vsproject.VisualProgrammingBackend.core.results.Result;
import com.vsproject.VisualProgrammingBackend.entity.StudentAttendance;

import java.util.List;

public interface AttendanceService {

    List<StudentAttendance> getAllAttendance(int page, int size);
    DataResult<List<StudentAttendanceResponse>> getAllAttendanceResponse(int page, int size);

    Result save(StudentAttendance attendance);

    Result takeAttendance(AttendanceCheckRequest request);

}
