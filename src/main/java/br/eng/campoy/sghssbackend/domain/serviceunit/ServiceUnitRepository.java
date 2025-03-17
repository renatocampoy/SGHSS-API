package br.eng.campoy.sghssbackend.domain.serviceunit;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ServiceUnitRepository extends JpaRepository<ServiceUnitEntity, Long> {
    Optional<ServiceUnitEntity> findByEmail(String email);
}