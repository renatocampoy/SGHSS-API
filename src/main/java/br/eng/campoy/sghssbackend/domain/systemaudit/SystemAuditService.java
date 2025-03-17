package br.eng.campoy.sghssbackend.domain.systemaudit;

import br.eng.campoy.sghssbackend.domain.systemaudit.dto.SystemAuditDto;
import br.eng.campoy.sghssbackend.domain.systemaudit.dto.SystemAuditResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SystemAuditService {

    private final SystemAuditRepository systemAuditRepository;

    @Autowired
    public SystemAuditService(SystemAuditRepository systemAuditRepository) {
        this.systemAuditRepository = systemAuditRepository;
    }

    public SystemAuditResponseDto createAuditEntry(SystemAuditDto dto) {
        SystemAuditEntity audit = new SystemAuditEntity();
        audit.setUsername(dto.getUsername());
        audit.setActionType(dto.getActionType());
        audit.setEntityName(dto.getEntityName());
        audit.setDetails(dto.getDetails());
        audit.setTimestamp(LocalDateTime.now());

        return convertToDto(systemAuditRepository.save(audit));
    }

    public List<SystemAuditResponseDto> listAllAudits() {
        return systemAuditRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SystemAuditResponseDto> listAuditsByUser(String username) {
        return systemAuditRepository.findByUsername(username).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<SystemAuditResponseDto> listAuditsByEntity(String entityName) {
        return systemAuditRepository.findByEntityName(entityName).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private SystemAuditResponseDto convertToDto(SystemAuditEntity entity) {
        return new SystemAuditResponseDto(
                entity.getId(),
                entity.getUsername(),
                entity.getActionType(),
                entity.getEntityName(),
                entity.getDetails(),
                entity.getTimestamp()
        );
    }
}