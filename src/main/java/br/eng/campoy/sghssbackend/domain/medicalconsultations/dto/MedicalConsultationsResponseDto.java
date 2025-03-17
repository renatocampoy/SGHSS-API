package br.eng.campoy.sghssbackend.domain.medicalconsultations.dto;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.ValueObject.MedicalConsultationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MedicalConsultationsResponseDto {
    private Long id;
    private String patientName;
    private String professionalName;
    private String serviceUnitName;
    private LocalDateTime scheduledDate;
    private LocalDateTime completedDate;
    private MedicalConsultationStatus status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}