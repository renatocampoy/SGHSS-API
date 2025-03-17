package br.eng.campoy.sghssbackend.domain.medicalprescription;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.MedicalConsultationsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MedicalPrescription")
public class MedicalPrescriptionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultation_id", nullable = false)
    private MedicalConsultationsEntity consultation;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalsEntity professional;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientsEntity patient;

    @Column(nullable = false)
    private String medication;

    @Column(nullable = false)
    private String dosage;

    @Column(nullable = false)
    private String instructions;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}