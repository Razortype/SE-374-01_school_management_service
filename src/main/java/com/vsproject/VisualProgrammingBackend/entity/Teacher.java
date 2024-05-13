package com.vsproject.VisualProgrammingBackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vsproject.VisualProgrammingBackend.core.enums.privateEnums.Profession;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teacher")
@EntityListeners(AuditingEntityListener.class)
public class Teacher extends User {

    @Enumerated(EnumType.STRING)
    private Profession profession;

    @Column(name = "upgraded_at")
    private LocalDateTime upgradedAt;

    // one-to-many course section
    @OneToMany(mappedBy = "teacher")
    @JsonIgnore
    private List<CourseSection> courseSections;

}
