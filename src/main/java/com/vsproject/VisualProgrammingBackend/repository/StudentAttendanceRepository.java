package com.vsproject.VisualProgrammingBackend.repository;

import com.vsproject.VisualProgrammingBackend.entity.StudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentAttendanceRepository extends JpaRepository<StudentAttendance, UUID> {
}
