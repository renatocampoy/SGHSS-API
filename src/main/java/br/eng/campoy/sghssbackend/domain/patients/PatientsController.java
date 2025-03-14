package br.eng.campoy.sghssbackend.domain.patients;

import br.eng.campoy.sghssbackend.domain.patients.dto.PatientsDto;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersPatientsDto;
import br.eng.campoy.sghssbackend.domain.users.ValueObject.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientsService patientsService;

    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL')")
    public ResponseEntity<UsersPatientsDto> createPatient(@RequestBody PatientsDto dto) {
        UsersPatientsDto patient = patientsService.createPatient(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL') or (hasRole('PACIENTE') and principal.patientId == #id)")
    public ResponseEntity<UsersPatientsDto> getPatientById(@PathVariable Long id) {
        UsersPatientsDto patient = patientsService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL') or (hasRole('PACIENTE') and principal.patientId == #id)")
    public ResponseEntity<UsersPatientsDto> updatePatient(@PathVariable Long id, @RequestBody PatientsDto dto) {
        UsersPatientsDto updatedPatient = patientsService.updatePatient(id, dto);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientsService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROFISSIONAL')")
    public ResponseEntity<List<UsersPatientsDto>> listPatients() {
        List<UsersPatientsDto> patients = patientsService.listPatients();
        return ResponseEntity.ok(patients);
    }
}