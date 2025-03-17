package br.eng.campoy.sghssbackend.domain.professionals;

import br.eng.campoy.sghssbackend.domain.professionals.dto.ProfessionalsDto;
import br.eng.campoy.sghssbackend.domain.professionals.dto.ProfessionalsResponseDto;
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
 * Controlador para gerenciamento de profissionais de saúde.
 */
@RestController
@RequestMapping("/professionals")
@Tag(name = "Profissionais de Saúde", description = "Gerenciamento de médicos, enfermeiros e outros profissionais da saúde")
public class ProfessionalsController {

    private final ProfessionalsService professionalsService;

    public ProfessionalsController(ProfessionalsService professionalsService) {
        this.professionalsService = professionalsService;
    }

    /**
     * Cria um novo profissional de saúde.
     *
     * @param dto Dados do profissional a ser criado.
     * @return Profissional criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar um profissional de saúde",
            description = "Cria um novo profissional de saúde no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Profissional criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProfessionalsResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<ProfessionalsResponseDto> createProfessional(@RequestBody ProfessionalsDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(professionalsService.createProfessional(dto));
    }

    /**
     * Obtém os detalhes de um profissional pelo ID.
     *
     * @param id ID do profissional.
     * @return Detalhes do profissional.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar profissional por ID",
            description = "Retorna os detalhes de um profissional de saúde específico.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profissional encontrado",
                            content = @Content(schema = @Schema(implementation = ProfessionalsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado")
            }
    )
    public ResponseEntity<ProfessionalsResponseDto> getProfessionalById(@PathVariable Long id) {
        return professionalsService.getProfessionalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os dados de um profissional de saúde.
     *
     * @param id  ID do profissional a ser atualizado.
     * @param dto Dados atualizados do profissional.
     * @return Profissional atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Atualizar profissional de saúde",
            description = "Atualiza os dados de um profissional de saúde cadastrado. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Profissional atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProfessionalsResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<ProfessionalsResponseDto> updateProfessional(@PathVariable Long id, @RequestBody ProfessionalsDto dto) {
        return ResponseEntity.ok(professionalsService.updateProfessional(id, dto));
    }

    /**
     * Exclui um profissional de saúde do sistema.
     *
     * @param id ID do profissional a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir profissional de saúde",
            description = "Exclui um profissional de saúde cadastrado no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Profissional excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Profissional não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteProfessional(@PathVariable Long id) {
        professionalsService.deleteProfessional(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os profissionais de saúde cadastrados no sistema.
     *
     * @return Lista de profissionais.
     */
    @GetMapping
    @Operation(
            summary = "Listar todos os profissionais de saúde",
            description = "Retorna uma lista de todos os profissionais de saúde cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de profissionais",
                            content = @Content(schema = @Schema(implementation = ProfessionalsResponseDto.class)))
            }
    )
    public ResponseEntity<List<ProfessionalsResponseDto>> listProfessionals() {
        return ResponseEntity.ok(professionalsService.listProfessionals());
    }
}