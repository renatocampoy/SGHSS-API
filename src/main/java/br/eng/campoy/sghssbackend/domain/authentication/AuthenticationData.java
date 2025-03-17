package br.eng.campoy.sghssbackend.domain.authentication;

import java.time.Instant;

public record AuthenticationData(String token, Instant expiry) {
}
