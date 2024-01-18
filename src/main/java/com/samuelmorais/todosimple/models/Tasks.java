package com.samuelmorais.todosimple.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.OverridesAttribute.List;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.util.Objects;
@Entity
@Table(name = Tasks.TABLE_NAME)
public class Tasks {
    public static final String TABLE_NAME = "tasks";

    // Temos que criar uma tabela pras tasks. Todas as tabelas tem id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Essa anotacao manda o valor ser gerado automaticamente, diz q o valor é uma id (identity)
    private Long id;


    /*
     * Toda task tem que estar associada a um usuario. Várias tasks podem ser associadas ao mesmo usuário. Para especificar isso, usamos a 
     * annotation '@ManyToOne'
     *
     * 
     */
    // Usuário que pode realizar a task
    // Os usuarios terao listas de tasks possiveis
    @ManyToOne  // Anotacao que indica que vários objetos Task serao associadas um usuario
    // JoinColumn é uma associacao entre tabelas
    /*
     * A partir de duas entitys (tabelas), a operacao de join consiste me estabeler uma relacao entre elas
     * usando uma coluna de cada. Essa relacao é definida utilizando anotacoes (onetomany, manytoone, etc)
     */
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // A coluna de referencias a objetos user
    // updatable diz se essa relacao com o objeto pode ser alterada
    private User user;

    // Descricao do que a task vai fazer
    @Column(name = "description", length = 250, nullable = false)
    private String description;


    public Tasks() {
    }

    public Tasks(Long id, User user, String description) {
        this.id = id;
        this.user = user;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tasks id(Long id) {
        setId(id);
        return this;
    }

    public Tasks user(User user) {
        setUser(user);
        return this;
    }

    public Tasks description(String description) {
        setDescription(description);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Tasks)) {
            return false;
        }
        Tasks tasks = (Tasks) o;
        return Objects.equals(id, tasks.id) && Objects.equals(user, tasks.user) && Objects.equals(description, tasks.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, description);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", user='" + getUser() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }

    // Static -> é uma característica do tipo, não de um objeto

}
