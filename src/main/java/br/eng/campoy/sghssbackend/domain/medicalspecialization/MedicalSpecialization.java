package br.eng.campoy.sghssbackend.domain.medicalspecialization;

import br.eng.campoy.sghssbackend.domain.medicalspecialization.dto.MedicalSpecializationDto;
import br.eng.campoy.sghssbackend.domain.medicalspecialization.dto.MedicalSpecializationResponseDto;
import br.eng.campoy.sghssbackend.domain.medicalspecialization.exception.MedicalSpecializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalSpecialization {

    private final MedicalSpecializationRepository medicalSpecializationRepository;

    @Autowired
    public MedicalSpecialization(MedicalSpecializationRepository medicalSpecializationRepository) {
        this.medicalSpecializationRepository = medicalSpecializationRepository;
    }

    public MedicalSpecializationResponseDto createSpecialization(MedicalSpecializationDto dto) {
        if (medicalSpecializationRepository.findByName(dto.getName()).isPresent()) {
            throw new MedicalSpecializationException("Especialização já existe.");
        }

        MedicalSpecializationEntity specialization = new MedicalSpecializationEntity();
        specialization.setName(dto.getName());

        return convertToDto(medicalSpecializationRepository.save(specialization));
    }

    public List<MedicalSpecializationResponseDto> listAllSpecializations() {
        return medicalSpecializationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<MedicalSpecializationResponseDto> getSpecializationById(Long id) {
        return medicalSpecializationRepository.findById(id).map(this::convertToDto);
    }

    public void deleteSpecialization(Long id) {
        MedicalSpecializationEntity specialization = medicalSpecializationRepository.findById(id)
                .orElseThrow(() -> new MedicalSpecializationException("Especialização não encontrada."));

        medicalSpecializationRepository.delete(specialization);
    }

    private MedicalSpecializationResponseDto convertToDto(MedicalSpecializationEntity entity) {
        return new MedicalSpecializationResponseDto(
                entity.getId(),
                entity.getName()
        );
    }
}