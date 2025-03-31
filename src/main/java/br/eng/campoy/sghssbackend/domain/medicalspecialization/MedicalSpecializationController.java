package br.eng.campoy.sghssbackend.domain.medicalspecialization;

import br.eng.campoy.sghssbackend.domain.medicalspecialization.dto.MedicalSpecializationDto;
import br.eng.campoy.sghssbackend.domain.medicalspecialization.dto.MedicalSpecializationResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de especializações médicas.
 */
@RestController
@RequestMapping("/medical-specializations")
@Tag(name = "Especializações Médicas", description = "Gerenciamento de especializações médicas disponíveis no sistema")
public class MedicalSpecializationController {

    private final MedicalSpecialization medicalSpecialization;

    public MedicalSpecializationController(MedicalSpecialization medicalSpecialization) {
        this.medicalSpecialization = medicalSpecialization;
    }

    /**
     * Cria uma nova especialização médica.
     *
     * @param dto Dados da especialização médica a ser criada.
     * @return Especialização médica criada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar uma especialização médica",
            description = "Cria uma nova especialização médica no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Especialização criada com sucesso",
                            content = @Content(schema = @Schema(implementation = MedicalSpecializationResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<MedicalSpecializationResponseDto> createSpecialization(@RequestBody MedicalSpecializationDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalSpecialization.createSpecialization(dto));
    }

    /**
     * Lista todas as especializações médicas cadastradas no sistema.
     *
     * @return Lista de especializações médicas.
     */
    @GetMapping
    @Operation(
            summary = "Listar todas as especializações médicas",
            description = "Retorna uma lista de todas as especializações médicas cadastradas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de especializações",
                            content = @Content(schema = @Schema(implementation = MedicalSpecializationResponseDto.class)))
            }
    )
    public ResponseEntity<List<MedicalSpecializationResponseDto>> listAllSpecializations() {
        return ResponseEntity.ok(medicalSpecialization.listAllSpecializations());
    }

    /**
     * Obtém os detalhes de uma especialização médica pelo ID.
     *
     * @param id ID da especialização médica.
     * @return Detalhes da especialização médica.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar especialização médica por ID",
            description = "Retorna os detalhes de uma especialização médica específica cadastrada no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Especialização encontrada",
                            content = @Content(schema = @Schema(implementation = MedicalSpecializationResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Especialização não encontrada")
            }
    )
    public ResponseEntity<MedicalSpecializationResponseDto> getSpecializationById(@PathVariable Long id) {
        return medicalSpecialization.getSpecializationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Exclui uma especialização médica do sistema.
     *
     * @param id ID da especialização médica a ser excluída.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir uma especialização médica",
            description = "Exclui uma especialização médica cadastrada no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Especialização excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Especialização não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteSpecialization(@PathVariable Long id) {
        medicalSpecialization.deleteSpecialization(id);
        return ResponseEntity.noContent().build();
    }
}