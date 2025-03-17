package br.eng.campoy.sghssbackend.domain.hospitalization;

import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.rooms.RoomsEntity;
import br.eng.campoy.sghssbackend.domain.hospitalization.ValueObject.HospitalizationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Hospitalization")
public class HospitalizationEntity {

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

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomsEntity room;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private HospitalizationStatus status;

    @Column(nullable = false)
    private LocalDateTime admissionDate;

    private LocalDateTime dischargeDate;
    private String reason;
    private String notes;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}