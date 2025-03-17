package br.eng.campoy.sghssbackend.domain.medicalconsultations.dto;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.ValueObject.MedicalConsultationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MedicalConsultationsDto {
    private Long patientId;
    private Long professionalId;
    private Long serviceUnitId;
    private LocalDateTime scheduledDate;
    private MedicalConsultationStatus status;
    private String notes;
}