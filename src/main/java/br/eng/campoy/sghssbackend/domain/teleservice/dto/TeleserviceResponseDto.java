package br.eng.campoy.sghssbackend.domain.teleservice.dto;

import br.eng.campoy.sghssbackend.domain.teleservice.ValueObject.TeleserviceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TeleserviceResponseDto {
    private Long id;
    private String consultationInfo;
    private String professionalName;
    private String patientName;
    private LocalDateTime scheduledDateTime;
    private TeleserviceStatus status;
}