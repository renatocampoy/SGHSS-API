package br.eng.campoy.sghssbackend.domain.teleservice.dto;

import br.eng.campoy.sghssbackend.domain.teleservice.ValueObject.TeleserviceStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TeleserviceDto {
    private Long consultationId;
    private Long professionalId;
    private Long patientId;
    private LocalDateTime scheduledDateTime;
    private TeleserviceStatus status;
}