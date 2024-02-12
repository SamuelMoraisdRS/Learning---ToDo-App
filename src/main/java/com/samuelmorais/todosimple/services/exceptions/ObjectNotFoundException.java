package com.samuelmorais.todosimple.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

// Essa classe será especificamente a response http para o caso do método get não encontrar a id
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends EntityNotFoundException {
    
    public ObjectNotFoundException(String message) {
        super(message); // Estamos chamando o código do construtor a superclasse e colocando agora
    }
}
