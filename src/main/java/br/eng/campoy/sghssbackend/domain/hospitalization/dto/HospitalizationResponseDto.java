package br.eng.campoy.sghssbackend.domain.hospitalization.dto;

import br.eng.campoy.sghssbackend.domain.hospitalization.ValueObject.HospitalizationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HospitalizationResponseDto {
    private Long id;
    private String patientName;
    private String professionalName;
    private String serviceUnitName;
    private String roomNumber;
    private LocalDateTime admissionDate;
    private LocalDateTime dischargeDate;
    private HospitalizationStatus status;
    private String reason;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}