package br.com.ekan.avaliacao.controller.exceptions;

import br.com.ekan.avaliacao.service.exceptions.DataIntegrityException;
import br.com.ekan.avaliacao.service.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
 import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseEntity<StandardError> internalAuthenticationServiceException(InternalAuthenticationServiceException e, HttpServletRequest request) {
        StandardError erro = new StandardError(
                HttpStatus.NOT_FOUND.value(),
                "Usuário inexistente ou senha inválida",
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        StandardError erro = new StandardError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<StandardError> dateTimeParseException(DateTimeParseException e, HttpServletRequest request) {
        StandardError erro = new StandardError(
                HttpStatus.BAD_REQUEST.value(),
                e.getMessage(),
                System.currentTimeMillis()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        StandardError erro = new StandardError(
				HttpStatus.NOT_FOUND.value(),
				e.getMessage(),
				System.currentTimeMillis()
		);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(DataIntegrityException.class)
    public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request) {
        StandardError erro = new StandardError(
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage(),
                System.currentTimeMillis()
		);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError erro = new ValidationError(
				HttpStatus.BAD_REQUEST.value(), "Erro de validação",
                System.currentTimeMillis()
		);

        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
			erro.addError(fe.getField(), fe.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

}