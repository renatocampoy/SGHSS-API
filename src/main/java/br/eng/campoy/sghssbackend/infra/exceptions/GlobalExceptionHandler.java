package br.eng.campoy.sghssbackend.infra.exceptions;

import br.eng.campoy.sghssbackend.exceptions.PatientsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

//    // Tratamento para exceções genéricas
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("message", "Erro inesperado ocorreu - " + ex.getMessage());
//        body.put("details", ex.getMessage());
//        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    // Tratamento de exceções específicas, como DataIntegrityViolationException
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Erro de integridade de dados.");
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Tratamento para MethodArgumentNotValidException - erro de validação
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                errors.put(fieldError.getField(), fieldError.getDefaultMessage()));

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Erro de validação");
        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    // Tratamento para exceções PatientsException
    @ExceptionHandler(PatientsException.class)
    public ResponseEntity<Object> handlePatientsException(PatientsException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        int status = Integer.parseInt(ex.getCause().getMessage().split(" ")[0]);
        return new ResponseEntity<>(body, HttpStatus.valueOf(status));
    }
}
