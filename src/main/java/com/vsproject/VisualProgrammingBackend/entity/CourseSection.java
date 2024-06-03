package com.vsproject.VisualProgrammingBackend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.WeekDay;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course_section")
@EntityListeners(AuditingEntityListener.class)
public class CourseSection {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "start_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @Column(name = "end_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @Column(name = "week_day")
    @Enumerated(EnumType.STRING)
    private WeekDay weekDay;

    // teacher many-to-one
    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    // course many-to-one
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "id", nullable = false)
    private Course course;

    // school class many-to-one
    @ManyToOne
    @JoinColumn(name = "school_class_id", referencedColumnName = "id", nullable = false)
    private SchoolClass schoolClass;

    @OneToMany(mappedBy = "courseSection")
    @JsonIgnore
    private List<StudentAttendance> attendanceList;

}
