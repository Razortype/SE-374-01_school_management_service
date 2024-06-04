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
@Table(name = "school_class")
@EntityListeners(AuditingEntityListener.class)
public class SchoolClass {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "class_name", unique = true)
    private String className;

    @Column(name = "class_code", unique = true)
    private String classCode;

    @OneToMany(mappedBy = "schoolClass")
    @JsonIgnore
    private List<CourseSection> courseSections;

    // registered students one-to-many
    @OneToMany(mappedBy = "schoolClass")
    @JsonIgnore
    private List<Student> students;

}
