package br.eng.campoy.sghssbackend.domain.serviceunit;

import br.eng.campoy.sghssbackend.domain.serviceunit.ValueObject.ServiceUnitStatus;
import br.eng.campoy.sghssbackend.domain.serviceunit.dto.ServiceUnitDto;
import br.eng.campoy.sghssbackend.domain.serviceunit.exception.ServiceUnitException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceUnit {

    private final ServiceUnitRepository serviceUnitRepository;

    @Autowired
    public ServiceUnit(ServiceUnitRepository serviceUnitRepository) {
        this.serviceUnitRepository = serviceUnitRepository;
    }

    public ServiceUnitDto createServiceUnit(ServiceUnitDto dto) {
        if (serviceUnitRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ServiceUnitException("E-mail já está em uso.");
        }

        ServiceUnitEntity serviceUnit = new ServiceUnitEntity();
        serviceUnit.setName(dto.getName());
        serviceUnit.setAddress(dto.getAddress());
        serviceUnit.setNumber(dto.getNumber());
        serviceUnit.setCity(dto.getCity());
        serviceUnit.setState(dto.getState());
        serviceUnit.setCountry(dto.getCountry());
        serviceUnit.setEmail(dto.getEmail());
        serviceUnit.setPhone(dto.getPhone());
        serviceUnit.setMobile(dto.getMobile());
        serviceUnit.setCapacity(dto.getCapacity());
        serviceUnit.setStatus(dto.getStatus());
        serviceUnit.setCreatedAt(LocalDateTime.now());
        serviceUnit.setUpdatedAt(LocalDateTime.now());

        ServiceUnitEntity savedUnit = serviceUnitRepository.save(serviceUnit);

        return convertToDto(savedUnit);
    }

    public ServiceUnitDto updateServiceUnit(Long id, ServiceUnitDto dto) {
        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(id)
                .orElseThrow(() -> new ServiceUnitException("Unidade de saúde não encontrada com ID: " + id));

        serviceUnit.setName(dto.getName());
        serviceUnit.setAddress(dto.getAddress());
        serviceUnit.setNumber(dto.getNumber());
        serviceUnit.setCity(dto.getCity());
        serviceUnit.setState(dto.getState());
        serviceUnit.setCountry(dto.getCountry());
        serviceUnit.setEmail(dto.getEmail());
        serviceUnit.setPhone(dto.getPhone());
        serviceUnit.setMobile(dto.getMobile());
        serviceUnit.setCapacity(dto.getCapacity());
        serviceUnit.setStatus(dto.getStatus());
        serviceUnit.setUpdatedAt(LocalDateTime.now());

        ServiceUnitEntity updatedUnit = serviceUnitRepository.save(serviceUnit);

        return convertToDto(updatedUnit);
    }

    public void deleteServiceUnit(Long id) {
        ServiceUnitEntity serviceUnit = serviceUnitRepository.findById(id)
                .orElseThrow(() -> new ServiceUnitException("Unidade de saúde não encontrada com ID: " + id));

        serviceUnit.setStatus(ServiceUnitStatus.DESATIVADO);
        serviceUnitRepository.save(serviceUnit);
    }

    public Optional<ServiceUnitDto> getServiceUnitById(Long id) {
        return serviceUnitRepository.findById(id).map(this::convertToDto);
    }

    public List<ServiceUnitDto> listServiceUnits() {
        return serviceUnitRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ServiceUnitDto convertToDto(ServiceUnitEntity entity) {
        ServiceUnitDto dto = new ServiceUnitDto();
        dto.setName(entity.getName());
        dto.setAddress(entity.getAddress());
        dto.setNumber(entity.getNumber());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setCountry(entity.getCountry());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setMobile(entity.getMobile());
        dto.setCapacity(entity.getCapacity());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}