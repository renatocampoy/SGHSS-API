package br.eng.campoy.sghssbackend.domain.authentication;

import java.time.Instant;

public record AuhtenticationData(String token, Instant expiry) {
}
