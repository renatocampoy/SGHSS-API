package br.eng.campoy.sghssbackend.domain.medicalconsultations;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.dto.MedicalConsultationsDto;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.dto.MedicalConsultationsResponseDto;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.ValueObject.MedicalConsultationStatus;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.exception.MedicalConsultationsException;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsRepository;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsRepository;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalConsultationsService {

    private final MedicalConsultationsRepository medicalConsultationRepository;
    private final PatientsRepository patientsRepository;
    private final ProfessionalsRepository professionalsRepository;
    private final ServiceUnitRepository serviceUnitRepository;

    @Autowired
    public MedicalConsultationsService(
            MedicalConsultationsRepository medicalConsultationRepository,
            PatientsRepository patientsRepository,
            ProfessionalsRepository professionalsRepository,
            ServiceUnitRepository serviceUnitRepository) {
        this.medicalConsultationRepository = medicalConsultationRepository;
        this.patientsRepository = patientsRepository;
        this.professionalsRepository = professionalsRepository;
        this.serviceUnitRepository = serviceUnitRepository;
    }

    public MedicalConsultationsResponseDto createMedicalConsultation(MedicalConsultationsDto dto) {
        PatientsEntity patient = patientsRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new MedicalConsultationsException("Paciente não encontrado."));

        ProfessionalsEntity professional = professionalsRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new MedicalConsultationsException("Profissional não encontrado."));

        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(dto.getServiceUnitId())
                .orElseThrow(() -> new MedicalConsultationsException("Unidade de saúde não encontrada."));

        MedicalConsultationsEntity consultation = new MedicalConsultationsEntity();
        consultation.setPatient(patient);
        consultation.setProfessional(professional);
        consultation.setServiceUnit(serviceUnit);
        consultation.setScheduledDate(dto.getScheduledDate());
        consultation.setStatus(MedicalConsultationStatus.AGENDADA);
        consultation.setNotes(dto.getNotes());
        consultation.setCreatedAt(LocalDateTime.now());
        consultation.setUpdatedAt(LocalDateTime.now());

        return convertToDto(medicalConsultationRepository.save(consultation));
    }

    public MedicalConsultationsResponseDto updateMedicalConsultation(Long id, MedicalConsultationsDto dto) {
        MedicalConsultationsEntity consultation = medicalConsultationRepository.findById(id)
                .orElseThrow(() -> new MedicalConsultationsException("Consulta não encontrada com ID: " + id));

        consultation.setScheduledDate(dto.getScheduledDate());
        consultation.setNotes(dto.getNotes());
        consultation.setUpdatedAt(LocalDateTime.now());

        return convertToDto(medicalConsultationRepository.save(consultation));
    }

    public void cancelMedicalConsultation(Long id) {
        MedicalConsultationsEntity consultation = medicalConsultationRepository.findById(id)
                .orElseThrow(() -> new MedicalConsultationsException("Consulta não encontrada com ID: " + id));

        consultation.setStatus(MedicalConsultationStatus.CANCELADA);
        consultation.setUpdatedAt(LocalDateTime.now());

        medicalConsultationRepository.save(consultation);
    }

    public Optional<MedicalConsultationsResponseDto> getMedicalConsultationById(Long id) {
        return medicalConsultationRepository.findById(id).map(this::convertToDto);
    }

    public List<MedicalConsultationsResponseDto> listAllMedicalConsultations() {
        return medicalConsultationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MedicalConsultationsResponseDto> listByPatient(Long patientId) {
        return medicalConsultationRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MedicalConsultationsResponseDto> listByProfessional(Long professionalId) {
        return medicalConsultationRepository.findByProfessionalId(professionalId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MedicalConsultationsResponseDto convertToDto(MedicalConsultationsEntity entity) {
        return new MedicalConsultationsResponseDto(
                entity.getId(),
                entity.getPatient().getName(),
                entity.getProfessional().getName(),
                entity.getServiceUnit().getName(),
                entity.getScheduledDate(),
                entity.getCompletedDate(),
                entity.getStatus(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}