package br.eng.campoy.sghssbackend.domain.professionals.dto;

import br.eng.campoy.sghssbackend.domain.professionals.ValueObject.ProfessionalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProfessionalsResponseDto {
    private Long id;
    private String name;
    private String cpf;
    private String classRegistration;
    private String serviceUnitName;
    private String email;
    private String phone;
    private String mobile;
    private ProfessionalStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}