package br.eng.campoy.sghssbackend.domain.rules;

import br.eng.campoy.sghssbackend.domain.rules.dto.RuleDto;
import br.eng.campoy.sghssbackend.domain.rules.dto.RuleResponseDto;
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
 * Controlador para gerenciamento de regras do sistema.
 */
@RestController
@RequestMapping("/rules")
@Tag(name = "Regras", description = "Gerenciamento de regras de autorização e acesso do sistema")
public class RuleController {

    private final RuleService ruleService;

    public RuleController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    /**
     * Cria uma nova regra de acesso.
     *
     * @param dto Dados da regra a ser criada.
     * @return Regra criada.
     */
    @PostMapping
    @Operation(
            summary = "Criar uma regra de acesso",
            description = "Cria uma nova regra de autorização no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Regra criada com sucesso",
                            content = @Content(schema = @Schema(implementation = RuleResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<RuleResponseDto> createRule(@RequestBody RuleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ruleService.createRule(dto));
    }

    /**
     * Lista todas as regras de acesso cadastradas no sistema.
     *
     * @return Lista de regras.
     */
    @GetMapping
    @Operation(
            summary = "Listar todas as regras",
            description = "Retorna uma lista de todas as regras de acesso cadastradas no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de regras",
                            content = @Content(schema = @Schema(implementation = RuleResponseDto.class)))
            }
    )
    public ResponseEntity<List<RuleResponseDto>> listAllRules() {
        return ResponseEntity.ok(ruleService.listAllRules());
    }

    /**
     * Exclui uma regra de acesso do sistema.
     *
     * @param id ID da regra a ser excluída.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir regra de acesso",
            description = "Exclui uma regra de autorização cadastrada no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Regra excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Regra não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
        return ResponseEntity.noContent().build();
    }
}