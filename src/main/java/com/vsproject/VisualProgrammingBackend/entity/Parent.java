package com.vsproject.VisualProgrammingBackend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "parent")
@EntityListeners(AuditingEntityListener.class)
public class Parent extends User {

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "upgraded_at")
    @CreationTimestamp
    private LocalDateTime upgradedAt;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<StudentParentRelation> studentRelations;

}
