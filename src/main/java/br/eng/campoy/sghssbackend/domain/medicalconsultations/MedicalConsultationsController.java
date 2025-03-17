package br.eng.campoy.sghssbackend.domain.medicalconsultations;

import br.eng.campoy.sghssbackend.domain.medicalconsultations.dto.MedicalConsultationsDto;
import br.eng.campoy.sghssbackend.domain.medicalconsultations.dto.MedicalConsultationsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medical-consultations")
public class MedicalConsultationsController {

    private final MedicalConsultationsService medicalConsultationService;

    public MedicalConsultationsController(MedicalConsultationsService medicalConsultationService) {
        this.medicalConsultationService = medicalConsultationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<MedicalConsultationsResponseDto> create(@RequestBody MedicalConsultationsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalConsultationService.createMedicalConsultation(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<MedicalConsultationsResponseDto> update(@PathVariable Long id, @RequestBody MedicalConsultationsDto dto) {
        return ResponseEntity.ok(medicalConsultationService.updateMedicalConsultation(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        medicalConsultationService.cancelMedicalConsultation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalConsultationsResponseDto> getById(@PathVariable Long id) {
        return medicalConsultationService.getMedicalConsultationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    public ResponseEntity<List<MedicalConsultationsResponseDto>> listAll() {
        return ResponseEntity.ok(medicalConsultationService.listAllMedicalConsultations());
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<MedicalConsultationsResponseDto>> listByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(medicalConsultationService.listByPatient(patientId));
    }

    @GetMapping("/professional/{professionalId}")
    public ResponseEntity<List<MedicalConsultationsResponseDto>> listByProfessional(@PathVariable Long professionalId) {
        return ResponseEntity.ok(medicalConsultationService.listByProfessional(professionalId));
    }
}