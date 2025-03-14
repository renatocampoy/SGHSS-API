package br.eng.campoy.sghssbackend.domain.patients;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PatientsRepository extends JpaRepository<PatientsEntity, Long> {
    Optional<PatientsEntity> findByCpf(String cpf);
    Optional<PatientsEntity> findByUserId(Long userId);
}