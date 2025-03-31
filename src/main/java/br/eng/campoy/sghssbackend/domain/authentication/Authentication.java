package br.eng.campoy.sghssbackend.domain.authentication;

import br.eng.campoy.sghssbackend.infra.security.JwtService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class Authentication {

    private final JwtService jwtService;

    public Authentication(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public ResponseEntity<AuthenticationData> authenticate(org.springframework.security.core.Authentication authentication) {
        AuthenticationData returnData = new AuthenticationData(
                jwtService.generateToken(authentication),
                Instant.now().plusSeconds(3600L)
        );

        if(returnData.token().isBlank()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(returnData);
    }
}
