package br.eng.campoy.sghssbackend.domain.professionals;

import br.eng.campoy.sghssbackend.domain.professionals.ValueObject.ProfessionalStatus;
import br.eng.campoy.sghssbackend.domain.professionals.dto.ProfessionalsDto;
import br.eng.campoy.sghssbackend.domain.professionals.dto.ProfessionalsResponseDto;
import br.eng.campoy.sghssbackend.domain.professionals.exception.ProfessionalsException;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitEntity;
import br.eng.campoy.sghssbackend.domain.serviceunit.ServiceUnitRepository;
import br.eng.campoy.sghssbackend.domain.users.UsersEntity;
import br.eng.campoy.sghssbackend.domain.users.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessionalsService {

    private final ProfessionalsRepository professionalsRepository;
    private final UsersRepository usersRepository;
    private final ServiceUnitRepository serviceUnitRepository;

    @Autowired
    public ProfessionalsService(ProfessionalsRepository professionalsRepository,
                                UsersRepository usersRepository,
                                ServiceUnitRepository serviceUnitRepository) {
        this.professionalsRepository = professionalsRepository;
        this.usersRepository = usersRepository;
        this.serviceUnitRepository = serviceUnitRepository;
    }

    public ProfessionalsResponseDto createProfessional(ProfessionalsDto dto) {
        UsersEntity user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ProfessionalsException("Usuário não encontrado."));

        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(dto.getServiceUnitId())
                .orElseThrow(() -> new ProfessionalsException("Unidade de saúde não encontrada."));

        ProfessionalsEntity professional = new ProfessionalsEntity();
        professional.setUser(user);
        professional.setServiceUnit(serviceUnit);
        professional.setName(dto.getName());
        professional.setCpf(dto.getCpf());
        professional.setClassRegistration(dto.getClassRegistration());
        professional.setAddress(dto.getAddress());
        professional.setNumber(dto.getNumber());
        professional.setCity(dto.getCity());
        professional.setState(dto.getState());
        professional.setCountry(dto.getCountry());
        professional.setEmail(dto.getEmail());
        professional.setPhone(dto.getPhone());
        professional.setPhoneContact(dto.getPhoneContact());
        professional.setMobile(dto.getMobile());
        professional.setStatus(dto.getStatus());
        professional.setCreatedAt(LocalDateTime.now());
        professional.setUpdatedAt(LocalDateTime.now());

        ProfessionalsEntity savedProfessional = professionalsRepository.save(professional);
        return convertToDto(savedProfessional);
    }

    public ProfessionalsResponseDto updateProfessional(Long id, ProfessionalsDto dto) {
        ProfessionalsEntity professional = professionalsRepository.findById(id)
                .orElseThrow(() -> new ProfessionalsException("Profissional não encontrado com ID: " + id));

        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(dto.getServiceUnitId())
                .orElseThrow(() -> new ProfessionalsException("Unidade de saúde não encontrada."));

        professional.setServiceUnit(serviceUnit);
        professional.setName(dto.getName());
        professional.setCpf(dto.getCpf());
        professional.setClassRegistration(dto.getClassRegistration());
        professional.setAddress(dto.getAddress());
        professional.setNumber(dto.getNumber());
        professional.setCity(dto.getCity());
        professional.setState(dto.getState());
        professional.setCountry(dto.getCountry());
        professional.setEmail(dto.getEmail());
        professional.setPhone(dto.getPhone());
        professional.setPhoneContact(dto.getPhoneContact());
        professional.setMobile(dto.getMobile());
        professional.setStatus(dto.getStatus());
        professional.setUpdatedAt(LocalDateTime.now());

        ProfessionalsEntity updatedProfessional = professionalsRepository.save(professional);
        return convertToDto(updatedProfessional);
    }

    public void deleteProfessional(Long id) {
        ProfessionalsEntity professional = professionalsRepository.findById(id)
                .orElseThrow(() -> new ProfessionalsException("Profissional não encontrado com ID: " + id));

        professional.setStatus(ProfessionalStatus.DESLIGADO);
        professionalsRepository.save(professional);
    }

    public Optional<ProfessionalsResponseDto> getProfessionalById(Long id) {
        return professionalsRepository.findById(id).map(this::convertToDto);
    }

    public List<ProfessionalsResponseDto> listProfessionals() {
        return professionalsRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ProfessionalsResponseDto convertToDto(ProfessionalsEntity entity) {
        return new ProfessionalsResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getCpf(),
                entity.getClassRegistration(),
                entity.getServiceUnit().getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getMobile(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}