package com.samuelmorais.todosimple.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.samuelmorais.todosimple.services.exceptions.DataBindingViolation;
import com.samuelmorais.todosimple.services.exceptions.ObjectNotFoundException;

import jakarta.validation.ConstraintViolationException;

import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;



// Essa classe será acionada sempre que uma excecao for detectada, e dará a resposta HTTP implementada por nós
// Seu comportamento é monitourado pelo spring
@Slf4j(topic = "GLOBAL_EXCEPTION_TRACE")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${server.error.include-exception}") // the annotation assigns the variable the value stated in the
                                                // .properties file
    private boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodArgumentNotValidException,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ErrorResponse error = new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Validation Error. Check the errors field for details\n");
        for (FieldError fieldError : methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            error.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(error);
    }

    // Excessao retornada quando o erro n foi capturada por métodos anteriores.
    // Se não temos uma exceção configurada para este caso, retornaremos uma
    // mensagem de unknown error
    // e o código 500 (unknown server error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception,
            WebRequest request) {
        // Quando aplicado a variáveis -> final == const
        final String errorMessage = "Unknown error ocurred.";
        log.error(errorMessage, exception);
        return buildErrorResponse(exception, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    // Tratará inputs inválidos do usuário
    // Este metodo será chamada se houver algum rompimento das integrity constraints
    // ou seja, inserir um valor já existente no banco ou alterar alguma coluna com
    // uma foreign
    // key para outra entidade.
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException dataIntegrityViolationException,
            WebRequest request) {
        String errorMessage = dataIntegrityViolationException.getMostSpecificCause().getMessage();
        log.error("Failed to save entity with integrity problems" + errorMessage,
                dataIntegrityViolationException);
        return buildErrorResponse(dataIntegrityViolationException,
                errorMessage, HttpStatus.CONFLICT,
                request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<Object> handleConstraintViolationException(
            ConstraintViolationException constraintViolationException,
            WebRequest request) {
        log.error("Failed to validate element", constraintViolationException);

        return buildErrorResponse(constraintViolationException, "Failed to validate element",
                HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException objectNotFoundException,
            WebRequest request) {
            log.error("Failed to find the requested element.", objectNotFoundException);
            return buildErrorResponse(objectNotFoundException,"Failed to find the requested element." , HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataBindingViolation.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<Object> handleDataBindingViolation(DataBindingViolation dataBindingViolation,
            WebRequest request) {
            log.error("Failed to save entity with associated data.", dataBindingViolation);
            return buildErrorResponse(dataBindingViolation,"Failed to save entity with associated data." , HttpStatus.NOT_FOUND, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception, String message,
            HttpStatus httpStatus, WebRequest request) {
        // é especificado o código da resposta recebida pelo servidor
        ErrorResponse errorResponse = new ErrorResponse(httpStatus.value(), message);
        if (printStackTrace) {
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

}