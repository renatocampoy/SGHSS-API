package br.eng.campoy.sghssbackend.domain.roles;

import br.eng.campoy.sghssbackend.domain.roles.dto.AssignRoleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável pela atribuição de funções (roles) a usuários.
 */
@RestController
@RequestMapping("/user-roles")
@Tag(name = "Atribuição de Funções", description = "Gerenciamento da atribuição de funções aos usuários do sistema")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * Atribui uma função a um usuário.
     *
     * @param dto Dados contendo o ID do usuário e a função a ser atribuída.
     * @return Status 201 (Criado) caso a atribuição seja bem-sucedida.
     */
    @PostMapping
    @Operation(
            summary = "Atribuir uma função a um usuário",
            description = "Associa uma função específica a um usuário no sistema.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Função atribuída com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos"),
                    @ApiResponse(responseCode = "404", description = "Usuário ou função não encontrados")
            }
    )
    public ResponseEntity<Void> assignRoleToUser(@RequestBody AssignRoleDto dto) {
        userRoleService.assignRoleToUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}