package br.eng.campoy.sghssbackend.domain.permissions.exception;

public class PermissionException extends RuntimeException {
    public PermissionException(String message) {
        super(message);
    }
}