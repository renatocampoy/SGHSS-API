package br.eng.campoy.sghssbackend.domain.teleservice;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.MedicalConsultationsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.teleservice.ValueObject.TeleserviceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Teleservice")
public class TeleserviceEntity {

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
    private LocalDateTime scheduledDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TeleserviceStatus status;
}