package br.eng.campoy.sghssbackend.domain.medicalconsultations;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicalConsultationsRepository extends JpaRepository<MedicalConsultationsEntity, Long> {
    List<MedicalConsultationsEntity> findByPatientId(Long patientId);
    List<MedicalConsultationsEntity> findByProfessionalId(Long professionalId);
}