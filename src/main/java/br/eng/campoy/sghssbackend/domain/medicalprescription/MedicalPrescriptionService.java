package br.eng.campoy.sghssbackend.domain.medicalprescription;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.MedicalConsultationsEntity;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.MedicalConsultationsRepository;
import br.eng.campoy.sghssbackend.domain.medicalprescription.dto.MedicalPrescriptionDto;
import br.eng.campoy.sghssbackend.domain.medicalprescription.dto.MedicalPrescriptionResponseDto;
import br.eng.campoy.sghssbackend.domain.medicalprescription.exception.MedicalPrescriptionException;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsRepository;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalPrescriptionService {

    private final MedicalPrescriptionRepository medicalPrescriptionRepository;
    private final MedicalConsultationsRepository medicalConsultationsRepository;
    private final ProfessionalsRepository professionalsRepository;
    private final PatientsRepository patientsRepository;

    @Autowired
    public MedicalPrescriptionService(MedicalPrescriptionRepository medicalPrescriptionRepository,
                                      MedicalConsultationsRepository medicalConsultationsRepository,
                                      ProfessionalsRepository professionalsRepository,
                                      PatientsRepository patientsRepository) {
        this.medicalPrescriptionRepository = medicalPrescriptionRepository;
        this.medicalConsultationsRepository = medicalConsultationsRepository;
        this.professionalsRepository = professionalsRepository;
        this.patientsRepository = patientsRepository;
    }

    public MedicalPrescriptionResponseDto createPrescription(MedicalPrescriptionDto dto) {
        MedicalConsultationsEntity consultation = medicalConsultationsRepository.findById(dto.getConsultationId())
                .orElseThrow(() -> new MedicalPrescriptionException("Consulta não encontrada."));

        ProfessionalsEntity professional = professionalsRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new MedicalPrescriptionException("Profissional não encontrado."));

        PatientsEntity patient = patientsRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new MedicalPrescriptionException("Paciente não encontrado."));

        MedicalPrescriptionEntity prescription = new MedicalPrescriptionEntity();
        prescription.setConsultation(consultation);
        prescription.setProfessional(professional);
        prescription.setPatient(patient);
        prescription.setMedication(dto.getMedication());
        prescription.setDosage(dto.getDosage());
        prescription.setInstructions(dto.getInstructions());
        prescription.setCreatedAt(LocalDateTime.now());

        return convertToDto(medicalPrescriptionRepository.save(prescription));
    }

    public List<MedicalPrescriptionResponseDto> listByConsultation(Long consultationId) {
        return medicalPrescriptionRepository.findByConsultationId(consultationId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MedicalPrescriptionResponseDto> listByPatient(Long patientId) {
        return medicalPrescriptionRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MedicalPrescriptionResponseDto convertToDto(MedicalPrescriptionEntity entity) {
        return new MedicalPrescriptionResponseDto(
                entity.getId(),
                "Consulta ID: " + entity.getConsultation().getId(),
                entity.getProfessional().getName(),
                entity.getPatient().getName(),
                entity.getMedication(),
                entity.getDosage(),
                entity.getInstructions(),
                entity.getCreatedAt()
        );
    }
}