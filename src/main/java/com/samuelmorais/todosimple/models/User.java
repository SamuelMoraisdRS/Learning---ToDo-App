package com.samuelmorais.todosimple.models;
// Model é a parte q processa e organiza os dados, vamos declarar os objetos do user


// Persistence -> a possibilidade de manter os dados atraves das diversas execuções do programa
// A JPA (Java Persistence API) é uma biblioteca que fornece mecanismos para gerenciar os dados
// abrangidos pelas persistencias
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.ArrayList;
import java.util.Objects;

// import jakarta.persistence.GenerationType;

/*
 *  Metadados
 *      - Dados que fornecem informacoes sobre outros dadso
 * Annotations (representadas pelo '@') carregam METADADOS, logo, são utilizadas para atribuir caracteristicas a objetos e métodos
 */

// Essa classe irá representar um usuario do site

@Entity // Definir a classe como entidade diz ao banco de dados (sql) que deve criar uma TABELA para estes objetos
@Table(name = User.TABLE_NAME) // Esta anotação irá declarar as características da tabela (Se nao especificarmos, a tabela tera informacoes padrao)
@AllArgsConstructor 	// Cria construtores com todos os argumentos
@NoArgsConstructor      // Cria construtores vazios, necessarios para a operacao do SpringBoot
@Getter
@Setter
@EqualsAndHashCode
public class User {
    // Conterá os métodos associados à criação do user e da atualizaçao do user
    public interface CreateUser {};
    public interface UpdateUser {};

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUser.class)    
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)   // Não retorna a senha para o frontend em momento algum, será permitida apenas a escrita da senha
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @NotEmpty(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 100)
    private String password;
    
    // Um user para varias tasks
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = Access.WRITE_ONLY)   // Qndo houver uma resposta JSON, deve retornar as tasks
    private java.util.List<Tasks> taskList = new ArrayList<Tasks>();    
}
