package br.eng.campoy.sghssbackend.domain.systemaudit.exception;

public class SystemAuditException extends RuntimeException {
    public SystemAuditException(String message) {
        super(message);
    }
}