package br.eng.campoy.sghssbackend.domain.hospitalizationreport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HospitalizationReportRepository extends JpaRepository<HospitalizationReportEntity, Long> {
    List<HospitalizationReportEntity> findByHospitalizationId(Long hospitalizationId);

    @Query("SELECT r FROM HospitalizationReportEntity r WHERE r.reportDate BETWEEN :startDate AND :endDate")
    List<HospitalizationReportEntity> findByReportDateBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}