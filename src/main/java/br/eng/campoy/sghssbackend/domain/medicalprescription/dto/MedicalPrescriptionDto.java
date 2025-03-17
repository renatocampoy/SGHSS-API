package br.eng.campoy.sghssbackend.domain.medicalprescription.dto;

import lombok.Data;

@Data
public class MedicalPrescriptionDto {
    private Long consultationId;
    private Long professionalId;
    private Long patientId;
    private String medication;
    private String dosage;
    private String instructions;
}