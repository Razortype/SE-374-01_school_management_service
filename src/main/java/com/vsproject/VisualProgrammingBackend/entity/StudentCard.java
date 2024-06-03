package com.vsproject.VisualProgrammingBackend.entity;

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
@Table(name = "student_card")
@EntityListeners(AuditingEntityListener.class)
public class StudentCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "card_nfc_token", nullable = false)
    private String cardNFCToken;

    @Column(name = "card_balance")
    private double cardBalance;

    @Column(name = "is_blocked")
    private boolean blocked;

    // student one-to-one
    @OneToOne(mappedBy = "card")
    private Student student;

}
