package com.vsproject.VisualProgrammingBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "course")
@EntityListeners(AuditingEntityListener.class)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "course_title", nullable = false)
    private String courseTitle;

    @Column(name = "course_code", nullable = false)
    private String courseCode;

    // course part one-to-many
    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private List<CourseSection> courseSections;


}
