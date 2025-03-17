package br.eng.campoy.sghssbackend.domain.systemaudit;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SystemAuditRepository extends JpaRepository<SystemAuditEntity, Long> {
    List<SystemAuditEntity> findByUsername(String username);
    List<SystemAuditEntity> findByEntityName(String entityName);
}