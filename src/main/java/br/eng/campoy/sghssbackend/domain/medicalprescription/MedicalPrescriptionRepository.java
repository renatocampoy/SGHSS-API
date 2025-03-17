package br.eng.campoy.sghssbackend.domain.medicalprescription;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicalPrescriptionRepository extends JpaRepository<MedicalPrescriptionEntity, Long> {
    List<MedicalPrescriptionEntity> findByConsultationId(Long consultationId);
    List<MedicalPrescriptionEntity> findByPatientId(Long patientId);
}