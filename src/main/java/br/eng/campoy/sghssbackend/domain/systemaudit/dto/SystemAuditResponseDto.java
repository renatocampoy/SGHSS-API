package br.eng.campoy.sghssbackend.domain.systemaudit.dto;

import br.eng.campoy.sghssbackend.domain.systemaudit.ValueObject.AuditActionType;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class SystemAuditResponseDto {
    private Long id;
    private String username;
    private AuditActionType actionType;
    private String entityName;
    private String details;
    private LocalDateTime timestamp;
}