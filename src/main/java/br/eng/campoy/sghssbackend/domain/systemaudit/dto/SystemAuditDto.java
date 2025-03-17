package br.eng.campoy.sghssbackend.domain.systemaudit.dto;

import br.eng.campoy.sghssbackend.domain.systemaudit.ValueObject.AuditActionType;
import lombok.Data;

@Data
public class SystemAuditDto {
    private String username;
    private AuditActionType actionType;
    private String entityName;
    private String details;
}