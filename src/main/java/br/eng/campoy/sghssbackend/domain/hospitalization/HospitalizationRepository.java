package br.eng.campoy.sghssbackend.domain.hospitalization;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HospitalizationRepository extends JpaRepository<HospitalizationEntity, Long> {
    List<HospitalizationEntity> findByPatientId(Long patientId);
    List<HospitalizationEntity> findByProfessionalId(Long professionalId);
    List<HospitalizationEntity> findByRoomId(Long roomId);
}