package br.eng.campoy.sghssbackend.domain.systemaudit;

import br.eng.campoy.sghssbackend.domain.systemaudit.dto.SystemAuditDto;
import br.eng.campoy.sghssbackend.domain.systemaudit.dto.SystemAuditResponseDto;
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
 * Controlador para auditoria do sistema.
 */
@RestController
@RequestMapping("/system-audit")
@Tag(name = "Auditoria do Sistema", description = "Gerenciamento de logs de auditoria para rastreamento de eventos no sistema")
public class SystemAuditController {

    private final SystemAudit systemAudit;

    public SystemAuditController(SystemAudit systemAudit) {
        this.systemAudit = systemAudit;
    }

    /**
     * Cria uma nova entrada de auditoria.
     *
     * @param dto Dados da auditoria a ser registrada.
     * @return Entrada de auditoria criada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar uma entrada de auditoria",
            description = "Registra um novo evento de auditoria no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Entrada de auditoria registrada com sucesso",
                            content = @Content(schema = @Schema(implementation = SystemAuditResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<SystemAuditResponseDto> createAuditEntry(@RequestBody SystemAuditDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(systemAudit.createAuditEntry(dto));
    }

    /**
     * Lista todas as entradas de auditoria registradas no sistema.
     *
     * @return Lista de auditorias.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar todas as entradas de auditoria",
            description = "Retorna uma lista com todas as auditorias registradas no sistema. Apenas administradores podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de auditorias",
                            content = @Content(schema = @Schema(implementation = SystemAuditResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<SystemAuditResponseDto>> listAllAudits() {
        return ResponseEntity.ok(systemAudit.listAllAudits());
    }

    /**
     * Lista as auditorias associadas a um usuário específico.
     *
     * @param username Nome de usuário a ser pesquisado.
     * @return Lista de auditorias associadas ao usuário.
     */
    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar auditorias por usuário",
            description = "Retorna todas as auditorias associadas a um usuário específico. Apenas administradores podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de auditorias do usuário",
                            content = @Content(schema = @Schema(implementation = SystemAuditResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma auditoria encontrada para o usuário"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<SystemAuditResponseDto>> listAuditsByUser(@PathVariable String username) {
        return ResponseEntity.ok(systemAudit.listAuditsByUser(username));
    }

    /**
     * Lista as auditorias associadas a uma entidade específica.
     *
     * @param entityName Nome da entidade a ser pesquisada.
     * @return Lista de auditorias associadas à entidade.
     */
    @GetMapping("/entity/{entityName}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar auditorias por entidade",
            description = "Retorna todas as auditorias associadas a uma entidade específica do sistema. Apenas administradores podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de auditorias da entidade",
                            content = @Content(schema = @Schema(implementation = SystemAuditResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Nenhuma auditoria encontrada para a entidade"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<SystemAuditResponseDto>> listAuditsByEntity(@PathVariable String entityName) {
        return ResponseEntity.ok(systemAudit.listAuditsByEntity(entityName));
    }
}