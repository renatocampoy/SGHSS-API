package br.eng.campoy.sghssbackend.domain.permissions;

import br.eng.campoy.sghssbackend.domain.permissions.dto.PermissionDto;
import br.eng.campoy.sghssbackend.domain.permissions.dto.PermissionResponseDto;
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
 * Controlador para gerenciamento de permissões de acesso no sistema.
 */
@RestController
@RequestMapping("/permissions")
@Tag(name = "Permissões", description = "Gerenciamento das permissões de acesso para os usuários do sistema")
public class PermissionController {

    private final Permission permission;

    public PermissionController(Permission permission) {
        this.permission = permission;
    }

    /**
     * Cria uma nova permissão de acesso.
     *
     * @param dto Dados da permissão a ser criada.
     * @return Permissão criada.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar uma permissão",
            description = "Cria uma nova permissão no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Permissão criada com sucesso",
                            content = @Content(schema = @Schema(implementation = PermissionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<PermissionResponseDto> createPermission(@RequestBody PermissionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permission.createPermission(dto));
    }

    /**
     * Lista todas as permissões cadastradas no sistema.
     *
     * @return Lista de permissões.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar todas as permissões",
            description = "Retorna uma lista de todas as permissões cadastradas no sistema. Apenas administradores podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de permissões",
                            content = @Content(schema = @Schema(implementation = PermissionResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<PermissionResponseDto>> listAllPermissions() {
        return ResponseEntity.ok(permission.listAllPermissions());
    }

    /**
     * Exclui uma permissão do sistema.
     *
     * @param id ID da permissão a ser excluída.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir uma permissão",
            description = "Exclui uma permissão cadastrada no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Permissão excluída com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Permissão não encontrada"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        permission.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}