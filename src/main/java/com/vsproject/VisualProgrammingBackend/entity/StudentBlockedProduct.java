package com.vsproject.VisualProgrammingBackend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "blocked_product")
@EntityListeners(AuditingEntityListener.class)
public class StudentBlockedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "block_reason")
    private String blockReason;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Product product;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Student student;

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)
    @JsonManagedReference
    private Parent parent;

}
