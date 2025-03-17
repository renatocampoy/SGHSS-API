package br.eng.campoy.sghssbackend.domain.teleservice;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.MedicalConsultationsEntity;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.MedicalConsultationsRepository;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsRepository;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsRepository;
import br.eng.campoy.sghssbackend.domain.teleservice.ValueObject.TeleserviceStatus;
import br.eng.campoy.sghssbackend.domain.teleservice.dto.TeleserviceDto;
import br.eng.campoy.sghssbackend.domain.teleservice.dto.TeleserviceResponseDto;
import br.eng.campoy.sghssbackend.domain.teleservice.exception.TeleserviceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeleserviceService {

    private final TeleserviceRepository teleserviceRepository;
    private final MedicalConsultationsRepository medicalConsultationsRepository;
    private final ProfessionalsRepository professionalsRepository;
    private final PatientsRepository patientsRepository;

    @Autowired
    public TeleserviceService(TeleserviceRepository teleserviceRepository,
                              MedicalConsultationsRepository medicalConsultationsRepository,
                              ProfessionalsRepository professionalsRepository,
                              PatientsRepository patientsRepository) {
        this.teleserviceRepository = teleserviceRepository;
        this.medicalConsultationsRepository = medicalConsultationsRepository;
        this.professionalsRepository = professionalsRepository;
        this.patientsRepository = patientsRepository;
    }

    public TeleserviceResponseDto createTeleservice(TeleserviceDto dto) {
        MedicalConsultationsEntity consultation = medicalConsultationsRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new TeleserviceException("Consulta não encontrada."));

        ProfessionalsEntity professional = professionalsRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new TeleserviceException("Profissional não encontrado."));

        PatientsEntity patient = patientsRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new TeleserviceException("Paciente não encontrado."));

        TeleserviceEntity teleservice = new TeleserviceEntity();
        teleservice.setConsultation(consultation);
        teleservice.setProfessional(professional);
        teleservice.setPatient(patient);
        teleservice.setScheduledDateTime(dto.getScheduledDateTime());
        teleservice.setStatus(dto.getStatus());

        return convertToDto(teleserviceRepository.save(teleservice));
    }

    public List<TeleserviceResponseDto> listByPatient(Long patientId) {
        return teleserviceRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TeleserviceResponseDto> listByProfessional(Long professionalId) {
        return teleserviceRepository.findByProfessionalId(professionalId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TeleserviceResponseDto convertToDto(TeleserviceEntity entity) {
        return new TeleserviceResponseDto(
                entity.getId(),
                "Consulta ID: " + entity.getConsultation().getId(),
                entity.getProfessional().getName(),
                entity.getPatient().getName(),
                entity.getScheduledDateTime(),
                entity.getStatus()
        );
    }

    public Optional<TeleserviceResponseDto> getTeleserviceById(Long id) {
        return teleserviceRepository.findById(id).map(this::convertToDto);
    }

    public TeleserviceResponseDto updateTeleserviceStatus(Long id, TeleserviceStatus status) {
        TeleserviceEntity teleservice = teleserviceRepository.findById(id)
                .orElseThrow(() -> new TeleserviceException("Teleatendimento não encontrado."));

        teleservice.setStatus(status);
        return convertToDto(teleserviceRepository.save(teleservice));
    }

    public void cancelTeleservice(Long id) {
        TeleserviceEntity teleservice = teleserviceRepository.findById(id)
                .orElseThrow(() -> new TeleserviceException("Teleatendimento não encontrado."));

        teleservice.setStatus(TeleserviceStatus.CANCELADO);
        teleserviceRepository.save(teleservice);
    }

    public List<TeleserviceResponseDto> listByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return teleserviceRepository.findByScheduledDateTimeBetween(startDate, endDate).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}