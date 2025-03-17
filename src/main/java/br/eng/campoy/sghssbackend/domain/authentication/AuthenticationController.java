package br.eng.campoy.sghssbackend.domain.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador responsável pela autenticação de usuários e geração de tokens JWT.
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Gerenciamento de autenticação e geração de tokens JWT")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Autentica um usuário e gera um token de acesso.
     *
     * @param authentication Dados de autenticação do usuário.
     * @return Dados da autenticação, incluindo o token JWT.
     */
    @PostMapping("/token")
    @Operation(
            summary = "Gerar token de autenticação",
            description = "Autentica um usuário e gera um token JWT válido para acesso ao sistema.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Autenticação bem-sucedida",
                            content = @Content(schema = @Schema(implementation = AuthenticationData.class))),
                    @ApiResponse(responseCode = "401", description = "Falha na autenticação - credenciais inválidas")
            }
    )
    public ResponseEntity<AuthenticationData> authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }
}
