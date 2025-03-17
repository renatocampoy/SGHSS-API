package br.eng.campoy.sghssbackend.domain.hospitalization.dto;

import br.eng.campoy.sghssbackend.domain.hospitalization.ValueObject.HospitalizationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HospitalizationDto {
    private Long patientId;
    private Long professionalId;
    private Long serviceUnitId;
    private Long roomId;
    private LocalDateTime admissionDate;
    private HospitalizationStatus status;
    private String reason;
    private String notes;
}