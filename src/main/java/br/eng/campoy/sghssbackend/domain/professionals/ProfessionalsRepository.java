package br.eng.campoy.sghssbackend.domain.professionals;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProfessionalsRepository extends JpaRepository<ProfessionalsEntity, Long> {
    Optional<ProfessionalsEntity> findByCpf(String cpf);
    List<ProfessionalsEntity> findByServiceUnitId(Long serviceUnitId);
}