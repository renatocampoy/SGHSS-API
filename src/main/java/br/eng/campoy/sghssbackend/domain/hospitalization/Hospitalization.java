package br.eng.campoy.sghssbackend.domain.hospitalization;

import br.eng.campoy.sghssbackend.domain.hospitalization.dto.HospitalizationDto;
import br.eng.campoy.sghssbackend.domain.hospitalization.dto.HospitalizationResponseDto;
import br.eng.campoy.sghssbackend.domain.hospitalization.ValueObject.HospitalizationStatus;
import br.eng.campoy.sghssbackend.domain.hospitalization.exception.HospitalizationException;
import br.eng.campoy.sghssbackend.domain.patients.PatientsEntity;
import br.eng.campoy.sghssbackend.domain.patients.PatientsRepository;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsEntity;
import br.eng.campoy.sghssbackend.domain.professionals.ProfessionalsRepository;
import br.eng.campoy.sghssbackend.domain.rooms.RoomsEntity;
import br.eng.campoy.sghssbackend.domain.rooms.RoomsRepository;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Hospitalization {

    private final HospitalizationRepository hospitalizationRepository;
    private final PatientsRepository patientsRepository;
    private final ProfessionalsRepository professionalsRepository;
    private final ServiceUnitRepository serviceUnitRepository;
    private final RoomsRepository roomsRepository;

    @Autowired
    public Hospitalization(
            HospitalizationRepository hospitalizationRepository,
            PatientsRepository patientsRepository,
            ProfessionalsRepository professionalsRepository,
            ServiceUnitRepository serviceUnitRepository,
            RoomsRepository roomsRepository) {
        this.hospitalizationRepository = hospitalizationRepository;
        this.patientsRepository = patientsRepository;
        this.professionalsRepository = professionalsRepository;
        this.serviceUnitRepository = serviceUnitRepository;
        this.roomsRepository = roomsRepository;
    }

    public HospitalizationResponseDto createHospitalization(HospitalizationDto dto) {
        PatientsEntity patient = patientsRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new HospitalizationException("Paciente não encontrado."));

        ProfessionalsEntity professional = professionalsRepository.findById(dto.getProfessionalId())
                .orElseThrow(() -> new HospitalizationException("Profissional não encontrado."));

        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(dto.getServiceUnitId())
                .orElseThrow(() -> new HospitalizationException("Unidade de saúde não encontrada."));

        RoomsEntity room = roomsRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new HospitalizationException("Quarto não encontrado."));

        HospitalizationEntity hospitalization = new HospitalizationEntity();
        hospitalization.setPatient(patient);
        hospitalization.setProfessional(professional);
        hospitalization.setServiceUnit(serviceUnit);
        hospitalization.setRoom(room);
        hospitalization.setAdmissionDate(dto.getAdmissionDate());
        hospitalization.setStatus(HospitalizationStatus.ATIVA);
        hospitalization.setReason(dto.getReason());
        hospitalization.setNotes(dto.getNotes());
        hospitalization.setCreatedAt(LocalDateTime.now());
        hospitalization.setUpdatedAt(LocalDateTime.now());

        return convertToDto(hospitalizationRepository.save(hospitalization));
    }

    public HospitalizationResponseDto updateHospitalization(Long id, HospitalizationDto dto) {
        HospitalizationEntity hospitalization = hospitalizationRepository.findById(id)
                .orElseThrow(() -> new HospitalizationException("Internação não encontrada com ID: " + id));

        hospitalization.setAdmissionDate(dto.getAdmissionDate());
        hospitalization.setReason(dto.getReason());
        hospitalization.setNotes(dto.getNotes());
        hospitalization.setUpdatedAt(LocalDateTime.now());

        return convertToDto(hospitalizationRepository.save(hospitalization));
    }

    public void dischargePatient(Long id) {
        HospitalizationEntity hospitalization = hospitalizationRepository.findById(id)
                .orElseThrow(() -> new HospitalizationException("Internação não encontrada com ID: " + id));

        hospitalization.setStatus(HospitalizationStatus.FINALIZADA);
        hospitalization.setDischargeDate(LocalDateTime.now());
        hospitalization.setUpdatedAt(LocalDateTime.now());

        hospitalizationRepository.save(hospitalization);
    }

    public void cancelHospitalization(Long id) {
        HospitalizationEntity hospitalization = hospitalizationRepository.findById(id)
                .orElseThrow(() -> new HospitalizationException("Internação não encontrada com ID: " + id));

        hospitalization.setStatus(HospitalizationStatus.CANCELADA);
        hospitalization.setUpdatedAt(LocalDateTime.now());

        hospitalizationRepository.save(hospitalization);
    }

    public Optional<HospitalizationResponseDto> getHospitalizationById(Long id) {
        return hospitalizationRepository.findById(id).map(this::convertToDto);
    }

    public List<HospitalizationResponseDto> listAllHospitalizations() {
        return hospitalizationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HospitalizationResponseDto> listByPatient(Long patientId) {
        return hospitalizationRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<HospitalizationResponseDto> listByProfessional(Long professionalId) {
        return hospitalizationRepository.findByProfessionalId(professionalId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private HospitalizationResponseDto convertToDto(HospitalizationEntity entity) {
        return new HospitalizationResponseDto(
                entity.getId(),
                entity.getPatient().getName(),
                entity.getProfessional().getName(),
                entity.getServiceUnit().getName(),
                entity.getRoom().getRoomNumber(),
                entity.getAdmissionDate(),
                entity.getDischargeDate(),
                entity.getStatus(),
                entity.getReason(),
                entity.getNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}