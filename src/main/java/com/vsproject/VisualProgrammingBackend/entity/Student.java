package com.vsproject.VisualProgrammingBackend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;
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

    @Column(name = "in_school")
    private boolean inSchool;

    @Column(name = "upgraded_at")
    private LocalDateTime upgradedAt;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<StudentParentRelation> parentRelations;

    // school class many-to-one
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private SchoolClass schoolClass;

    // attendance one-to-many
    @OneToMany(mappedBy = "student")
    @JsonIgnore
    private List<StudentAttendance> attendanceList;

    // one to one card
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "card_id", referencedColumnName = "id")
    private StudentCard card;

    @OneToMany(mappedBy = "student")
    @JsonBackReference
    private List<Transaction> transactions;


}
