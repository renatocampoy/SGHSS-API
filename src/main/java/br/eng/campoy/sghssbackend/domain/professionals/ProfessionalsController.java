package br.eng.campoy.sghssbackend.domain.professionals;

import br.eng.campoy.sghssbackend.domain.professionals.dto.ProfessionalsDto;
import br.eng.campoy.sghssbackend.domain.professionals.dto.ProfessionalsResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professionals")
public class ProfessionalsController {

    private final ProfessionalsService professionalsService;

    public ProfessionalsController(ProfessionalsService professionalsService) {
        this.professionalsService = professionalsService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessionalsResponseDto> createProfessional(@RequestBody ProfessionalsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professionalsService.createProfessional(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalsResponseDto> getProfessionalById(@PathVariable Long id) {
        return professionalsService.getProfessionalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProfessionalsResponseDto> updateProfessional(@PathVariable Long id, @RequestBody ProfessionalsDto dto) {
        return ResponseEntity.ok(professionalsService.updateProfessional(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        professionalsService.deleteProfessional(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ProfessionalsResponseDto>> listProfessionals() {
        return ResponseEntity.ok(professionalsService.listProfessionals());
    }
}