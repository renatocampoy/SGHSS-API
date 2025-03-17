package br.eng.campoy.sghssbackend.domain.serviceunit;

import br.eng.campoy.sghssbackend.domain.serviceunit.dto.ServiceUnitDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/service-units")
public class ServiceUnitController {

    private final ServiceUnitService serviceUnitService;

    public ServiceUnitController(ServiceUnitService serviceUnitService) {
        this.serviceUnitService = serviceUnitService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceUnitDto> createServiceUnit(@RequestBody ServiceUnitDto dto) {
        ServiceUnitDto serviceUnit = serviceUnitService.createServiceUnit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceUnit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceUnitDto> getServiceUnitById(@PathVariable Long id) {
        return serviceUnitService.getServiceUnitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceUnitDto> updateServiceUnit(@PathVariable Long id, @RequestBody ServiceUnitDto dto) {
        ServiceUnitDto updatedServiceUnit = serviceUnitService.updateServiceUnit(id, dto);
        return ResponseEntity.ok(updatedServiceUnit);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteServiceUnit(@PathVariable Long id) {
        serviceUnitService.deleteServiceUnit(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ServiceUnitDto>> listServiceUnits() {
        List<ServiceUnitDto> serviceUnits = serviceUnitService.listServiceUnits();
        return ResponseEntity.ok(serviceUnits);
    }
}