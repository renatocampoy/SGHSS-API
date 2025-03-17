package br.eng.campoy.sghssbackend.domain.hospitalizationreport;

import br.eng.campoy.sghssbackend.domain.hospitalization.HospitalizationEntity;
import br.eng.campoy.sghssbackend.domain.hospitalization.HospitalizationRepository;
import br.eng.campoy.sghssbackend.domain.hospitalizationreport.dto.HospitalizationReportDto;
import br.eng.campoy.sghssbackend.domain.hospitalizationreport.dto.HospitalizationReportResponseDto;
import br.eng.campoy.sghssbackend.domain.hospitalizationreport.exception.HospitalizationReportException;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalizationReportService {

    private final HospitalizationReportRepository hospitalizationReportRepository;
    private final HospitalizationRepository hospitalizationRepository;
    private final ProfessionalsRepository professionalsRepository;

    @Autowired
    public HospitalizationReportService(HospitalizationReportRepository hospitalizationReportRepository,
                                        HospitalizationRepository hospitalizationRepository,
                                        ProfessionalsRepository professionalsRepository) {
        this.hospitalizationReportRepository = hospitalizationReportRepository;
        this.hospitalizationRepository = hospitalizationRepository;
        this.professionalsRepository = professionalsRepository;
    }

    public HospitalizationReportResponseDto createReport(HospitalizationReportDto dto) {
        HospitalizationEntity hospitalization = hospitalizationRepository.findById(dto.getHospitalizationId())
                .orElseThrow(() -> new HospitalizationReportException("Internação não encontrada."));

        ProfessionalsEntity professional = professionalsRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new HospitalizationReportException("Profissional não encontrado."));

        HospitalizationReportEntity report = new HospitalizationReportEntity();
        report.setHospitalization(hospitalization);
        report.setProfessional(professional);
        report.setReportText(dto.getReportText());
        report.setReportDate(dto.getReportDate());

        return convertToDto(hospitalizationReportRepository.save(report));
    }

    public Optional<HospitalizationReportResponseDto> getReportById(Long id) {
        return hospitalizationReportRepository.findById(id).map(this::convertToDto);
    }

    public List<HospitalizationReportResponseDto> listReportsByHospitalization(Long hospitalizationId) {
        return hospitalizationReportRepository.findByHospitalizationId(hospitalizationId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private HospitalizationReportResponseDto convertToDto(HospitalizationReportEntity entity) {
        return new HospitalizationReportResponseDto(
                entity.getId(),
                "Internação ID: " + entity.getHospitalization().getId(),
                entity.getProfessional().getName(),
                entity.getReportText(),
                entity.getReportDate()
        );
    }

    public HospitalizationReportResponseDto updateReport(Long id, HospitalizationReportDto dto) {
        HospitalizationReportEntity report = hospitalizationReportRepository.findById(id)
                .orElseThrow(() -> new HospitalizationReportException("Relatório não encontrado com ID: " + id));

        report.setReportText(dto.getReportText());
        report.setReportDate(dto.getReportDate());

        return convertToDto(hospitalizationReportRepository.save(report));
    }

    public void deleteReport(Long id) {
        HospitalizationReportEntity report = hospitalizationReportRepository.findById(id)
                .orElseThrow(() -> new HospitalizationReportException("Relatório não encontrado com ID: " + id));

        hospitalizationReportRepository.delete(report);
    }

    public List<HospitalizationReportResponseDto> listReportsByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return hospitalizationReportRepository.findByReportDateBetween(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}