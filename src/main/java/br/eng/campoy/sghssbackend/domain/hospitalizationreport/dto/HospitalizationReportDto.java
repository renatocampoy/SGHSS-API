package br.eng.campoy.sghssbackend.domain.hospitalizationreport.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HospitalizationReportDto {
    private Long hospitalizationId;
    private Long professionalId;
    private String reportText;
    private LocalDateTime reportDate;
}