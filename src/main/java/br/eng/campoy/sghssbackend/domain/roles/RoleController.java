package br.eng.campoy.sghssbackend.domain.roles;

import br.eng.campoy.sghssbackend.domain.roles.dto.RoleDto;
import br.eng.campoy.sghssbackend.domain.roles.dto.RoleResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciamento de papéis (roles) no sistema.
 */
@RestController
@RequestMapping("/roles")
@Tag(name = "Papéis (Roles)", description = "Gerenciamento de permissões e papéis de usuários no sistema")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    /**
     * Cria um novo papel (role) no sistema.
     *
     * @param dto Dados do papel a ser criado.
     * @return Papel criado.
     */
    @PostMapping
    @Operation(
            summary = "Criar um papel (role)",
            description = "Cria um novo papel no sistema, definindo permissões para usuários.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Papel criado com sucesso",
                            content = @Content(schema = @Schema(implementation = RoleResponseDto.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<RoleResponseDto> createRole(@RequestBody RoleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.createRole(dto));
    }

    /**
     * Lista todos os papéis (roles) cadastrados no sistema.
     *
     * @return Lista de papéis.
     */
    @GetMapping
    @Operation(
            summary = "Listar todos os papéis (roles)",
            description = "Retorna uma lista de todos os papéis de usuários cadastrados no sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de papéis",
                            content = @Content(schema = @Schema(implementation = RoleResponseDto.class)))
            }
    )
    public ResponseEntity<List<RoleResponseDto>> listAllRoles() {
        return ResponseEntity.ok(roleService.listAllRoles());
    }

    /**
     * Exclui um papel (role) do sistema.
     *
     * @param id ID do papel a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Excluir um papel (role)",
            description = "Exclui um papel cadastrado no sistema.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Papel excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Papel não encontrado")
            }
    )
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}