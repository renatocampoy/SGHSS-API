package br.eng.campoy.sghssbackend.domain.medicalspecialization.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MedicalSpecializationResponseDto {
    private Long id;
    private String name;
}