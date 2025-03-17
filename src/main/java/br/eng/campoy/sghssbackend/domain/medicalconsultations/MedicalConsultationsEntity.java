package br.eng.campoy.sghssbackend.domain.medicalconsultations;

import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.ValueObject.MedicalConsultationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "MedicalConsultations")
public class MedicalConsultationsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientsEntity patient;

    @ManyToOne
    @JoinColumn(name = "professional_id", nullable = false)
    private ProfessionalsEntity professional;

    @ManyToOne
    @JoinColumn(name = "service_unit_id", nullable = false)
    private ServiceUnitEntity serviceUnit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicalConsultationStatus status;

    @Column(nullable = false)
    private LocalDateTime scheduledDate;

    private LocalDateTime completedDate;
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}