package com.samuelmorais.todosimple.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.samuelmorais.todosimple.models.Tasks;
import com.samuelmorais.todosimple.models.User;
import com.samuelmorais.todosimple.services.TaskService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/task")
@Validated
public class TaskController {
    @Autowired  // Faz a insercao da dependencia
    private TaskService taskService;
    // @Autowired
    // private UserController userController;


    @GetMapping("/{id}")
    @Validated
    // ResponseEntity é tipo associado às responses do protocolo HTTP
    public ResponseEntity<Tasks> findById(@PathVariable Long id) { // PathVariable informa que o nome da variavel deve substitui id no path
        Tasks obj = taskService.findByID(id);
        return ResponseEntity.ok().body(obj);   // Os dados do retorno estarão anexados ao body da response
    }

    // Quem define o get a ser usado é o path inserido
    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Tasks>> findAllByUser_Id(@PathVariable Long user_id) {
        List<Tasks> list = taskService.findAllByUserId(user_id);
        return ResponseEntity.ok().body(list);
    }


    @PostMapping
    @Validated
    public ResponseEntity<Tasks> create(@RequestBody @Valid Tasks obj) {
        taskService.create(obj);    
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    @PutMapping("/{id}")
    @Validated
    // RequestBody especifica que o objeto será extraido do body do protocolo HTTP. O body deve ser um arquivo do tipo task
    public ResponseEntity<Void> update(@Valid @RequestBody Tasks obj, @PathVariable Long id) {   // O param id é necessário para criar o nome do path
        obj.setId(id);
        taskService.update(obj);        
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Validated
    public ResponseEntity<Void> delete (@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
