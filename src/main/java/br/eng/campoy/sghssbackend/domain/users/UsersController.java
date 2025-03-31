package br.eng.campoy.sghssbackend.domain.users;

import br.eng.campoy.sghssbackend.domain.users.dto.UsersDto;
import br.eng.campoy.sghssbackend.domain.users.dto.UsersResponseDto;
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
 * Controlador para gerenciamento de usuários no sistema.
 */
@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "Gerenciamento de usuários do sistema")
public class UsersController {

    private final Users users;

    public UsersController(Users users) {
        this.users = users;
    }

    /**
     * Cria um novo usuário.
     *
     * @param dto Dados do usuário a ser criado.
     * @return Usuário criado.
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Criar um usuário",
            description = "Cria um novo usuário no sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsersResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos")
            }
    )
    public ResponseEntity<UsersResponseDto> createUser(@RequestBody UsersDto dto) {
        UsersResponseDto user = users.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * Obtém os detalhes de um usuário específico pelo ID.
     *
     * @param id ID do usuário.
     * @return Detalhes do usuário.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROFISSIONAL') and principal.id == #id)")
    @Operation(
            summary = "Buscar usuário por ID",
            description = "Retorna os detalhes de um usuário específico. Administradores podem buscar qualquer usuário; profissionais podem buscar apenas seus próprios dados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                            content = @Content(schema = @Schema(implementation = UsersResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<UsersResponseDto> getUserById(@PathVariable Long id) {
        return users.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Atualiza os dados de um usuário.
     *
     * @param id  ID do usuário a ser atualizado.
     * @param dto Dados do usuário atualizados.
     * @return Usuário atualizado.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('PROFISSIONAL') and principal.id == #id)")
    @Operation(
            summary = "Atualizar usuário",
            description = "Atualiza os dados de um usuário. Administradores podem atualizar qualquer usuário; profissionais podem atualizar apenas seus próprios dados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = UsersResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<UsersResponseDto> updateUser(@PathVariable Long id, @RequestBody UsersDto dto) {
        UsersResponseDto updatedUser = users.updateUser(id, dto);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Exclui um usuário do sistema.
     *
     * @param id ID do usuário a ser excluído.
     * @return Status 204 (Sem conteúdo).
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Excluir usuário",
            description = "Exclui um usuário do sistema. Apenas administradores podem executar essa ação.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        users.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Lista todos os usuários do sistema.
     *
     * @return Lista de usuários.
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Listar todos os usuários",
            description = "Retorna uma lista de todos os usuários do sistema. Apenas administradores podem visualizar.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de usuários",
                            content = @Content(schema = @Schema(implementation = UsersResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Acesso negado")
            }
    )
    public ResponseEntity<List<UsersResponseDto>> listUsers() {
        return ResponseEntity.ok(users.listUsers());
    }
}