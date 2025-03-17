package br.eng.campoy.sghssbackend.domain.medicalprescription.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MedicalPrescriptionResponseDto {
    private Long id;
    private String consultationInfo;
    private String professionalName;
    private String patientName;
    private String medication;
    private String dosage;
    private String instructions;
    private LocalDateTime createdAt;
}