package com.vsproject.VisualProgrammingBackend.entity;

import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.AttendanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_attendance")
@EntityListeners(AuditingEntityListener.class)
public class StudentAttendance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "attendance_type")
    @Enumerated(EnumType.STRING)
    private AttendanceType attendanceType;

    @Column(name = "excuse_description")
    private String excuseDescription;

    @Column(name = "is_absent")
    private boolean absent;

    @Column(name = "attendance_date")
    @CreationTimestamp
    private LocalDateTime attendanceDate;

    // student many-to-one
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

}
