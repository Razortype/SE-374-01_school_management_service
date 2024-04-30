package com.vsproject.VisualProgrammingBackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class)
public class Student extends User {

    @Column(name = "school_number", unique = true)
    private String schoolNumber;

    @Column(name = "class_name")
    private String className;

    @Column(name = "in_school")
    private boolean inSchool;

    @Column(name = "upgraded_at")
    private LocalDateTime upgradedAt;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentParentRelation> parentRelations;

    // one to one card

}
