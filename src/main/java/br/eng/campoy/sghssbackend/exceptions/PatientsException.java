package br.eng.campoy.sghssbackend.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Exceção personalizada para erros relacionados à entidade ou serviço de pacientes.
 */
public class PatientsException extends RuntimeException {

    /**
     * Construtor padrão com mensagem de erro.
     *
     * @param message Mensagem detalhada sobre o erro.
     */
    public PatientsException(String message) {
        super(message);
    }

    /**
     * Construtor para mensagem de erro e causa.
     *
     * @param message Mensagem detalhada sobre o erro.
     * @param cause   Causa original do erro.
     */
    public PatientsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Construtor para causa.
     *
     * @param cause Causa original do erro.
     */
    public PatientsException(Throwable cause) {
        super(cause);
    }

    public PatientsException(String message, HttpStatus httpStatus) {
        super(message, new Throwable(httpStatus.toString()));
    }
}