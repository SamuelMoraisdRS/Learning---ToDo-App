package com.samuelmorais.todosimple.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.samuelmorais.todosimple.models.User;
import com.samuelmorais.todosimple.models.User.CreateUser;
import com.samuelmorais.todosimple.models.User.UpdateUser;
import com.samuelmorais.todosimple.services.UserService;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Essa é a camada da arquitetura que fará a comunicacao das requisicoes recebidas do front end com a camada de service
 * Ou seja, é necessário estabelecer uma conexão entre as requisicoes HTTP e os métodos construídos nas outras camadas
 * 
 */

@RestController
@RequestMapping("/user") // Rota base dessas chamadas será a user
@Validated // Qualquer ação deve ser validada
public class UserController {

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  
    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) { // PathVariable informa que a id deve ser extraida da uri
        User obj = userService.findByID(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    @Validated(CreateUser.class)    // A operacao deve ser validada (aprovada ou  n) utilizando as anotacoes aplicadas à
    public ResponseEntity<Void> create(@Valid @RequestBody User obj) {
        logger.debug("Debug na requisição Create User {}", obj);
        userService.create(obj);
        // ServletUriComponentsBuilder é um objeto utilizado para construir URIs
        // path -> adiciona o caminho à rota atual
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").
                  buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated(UpdateUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User obj, @PathVariable Long id) {
       obj.setId(id);
        userService.findByID(id);
        userService.update(obj);        
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
