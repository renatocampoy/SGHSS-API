package br.eng.campoy.sghssbackend.domain.medicalspecialization;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MedicalSpecializationRepository extends JpaRepository<MedicalSpecializationEntity, Long> {
    Optional<MedicalSpecializationEntity> findByName(String name);
}