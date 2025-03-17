package br.eng.campoy.sghssbackend.domain.hospitalization;

import br.eng.campoy.sghssbackend.domain.hospitalization.dto.HospitalizationDto;
import br.eng.campoy.sghssbackend.domain.hospitalization.dto.HospitalizationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gerenciamento de internações hospitalares.
 */
@RestController
@RequestMapping("/hospitalizations")
@Tag(name = "Internações Hospitalares", description = "Gerenciamento de internações de pacientes")
public class HospitalizationController {

    private final HospitalizationService hospitalizationService;

    public HospitalizationController(HospitalizationService hospitalizationService) {
        this.hospitalizationService = hospitalizationService;
    }

    /**
     * Registra uma nova internação hospitalar.
     *
     * @param dto Dados da internação a ser criada.
     * @return Internação hospitalar registrada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Registrar uma internação",
            description = "Registra uma nova internação hospitalar para um paciente. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Internação registrada com sucesso",
                            content = @Content(schema = @Schema(implementation = HospitalizationResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<HospitalizationResponseDto> create(@RequestBody HospitalizationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hospitalizationService.createHospitalization(dto));
    }

    /**
     * Atualiza os dados de uma internação hospitalar existente.
     *
     * @param id  ID da internação a ser atualizada.
     * @param dto Dados atualizados da internação.
     * @return Internação hospitalar atualizada.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Atualizar uma internação",
            description = "Atualiza os dados de uma internação hospitalar existente. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Internação atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = HospitalizationResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Internação não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<HospitalizationResponseDto> update(@PathVariable Long id, @RequestBody HospitalizationDto dto) {
        return ResponseEntity.ok(hospitalizationService.updateHospitalization(id, dto));
    }

    /**
     * Cancela uma internação hospitalar.
     *
     * @param id ID da internação a ser cancelada.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Cancelar uma internação",
            description = "Cancela uma internação hospitalar cadastrada no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Internação cancelada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Internação não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        hospitalizationService.cancelHospitalization(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Realiza a alta do paciente de uma internação.
     *
     * @param id ID da internação.
     * @return Status 204 (Sem conteúdo).
     */
    @PatchMapping("/{id}/discharge")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFISSIONAL')")
    @Operation(
            summary = "Dar alta a um paciente internado",
            description = "Registra a alta de um paciente internado. Apenas administradores e profissionais de saúde podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Alta registrada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Internação não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> discharge(@PathVariable Long id) {
        hospitalizationService.dischargePatient(id);
        return ResponseEntity.noContent().build();
    }
}