package br.eng.campoy.sghssbackend.domain.hospitalization;

import br.eng.campoy.sghssbackend.domain.hospitalization.dto.HospitalizationDto;
import br.eng.campoy.sghssbackend.domain.hospitalization.dto.HospitalizationResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospitalizations")
public class HospitalizationController {

    private final HospitalizationService hospitalizationService;

    public HospitalizationController(HospitalizationService hospitalizationService) {
        this.hospitalizationService = hospitalizationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<HospitalizationResponseDto> create(@RequestBody HospitalizationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalizationService.createHospitalization(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<HospitalizationResponseDto> update(@PathVariable Long id, @RequestBody HospitalizationDto dto) {
        return ResponseEntity.ok(hospitalizationService.updateHospitalization(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        hospitalizationService.cancelHospitalization(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/discharge")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<Void> discharge(@PathVariable Long id) {
        hospitalizationService.dischargePatient(id);
        return ResponseEntity.noContent().build();
    }
}