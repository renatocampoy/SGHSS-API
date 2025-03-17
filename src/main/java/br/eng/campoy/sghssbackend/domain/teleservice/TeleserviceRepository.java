package br.eng.campoy.sghssbackend.domain.teleservice;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeleserviceRepository extends JpaRepository<TeleserviceEntity, Long> {
    List<TeleserviceEntity> findByPatientId(Long patientId);
    List<TeleserviceEntity> findByProfessionalId(Long professionalId);
    @Query("SELECT t FROM TeleserviceEntity t WHERE t.scheduledDateTime BETWEEN :startDate AND :endDate")
    List<TeleserviceEntity> findByScheduledDateTimeBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}