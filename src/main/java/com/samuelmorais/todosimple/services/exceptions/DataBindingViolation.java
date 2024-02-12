package com.samuelmorais.todosimple.services.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.CONFLICT)
public class DataBindingViolation extends DataIntegrityViolationException {
    public DataBindingViolation(String message) {
        super(message);
    }
}
