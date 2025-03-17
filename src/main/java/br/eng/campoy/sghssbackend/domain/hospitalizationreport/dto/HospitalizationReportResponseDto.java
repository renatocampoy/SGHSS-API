package br.eng.campoy.sghssbackend.domain.hospitalizationreport.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class HospitalizationReportResponseDto {
    private Long id;
    private String hospitalizationInfo;
    private String professionalName;
    private String reportText;
    private LocalDateTime reportDate;
}