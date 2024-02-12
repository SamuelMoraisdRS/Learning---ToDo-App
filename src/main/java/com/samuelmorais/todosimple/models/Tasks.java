package com.samuelmorais.todosimple.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;


import java.util.Objects;
@Entity
@Table(name = Tasks.TABLE_NAME) //Estamos modelando um banco de dados SQL a partir da classe (criando uma tabela)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Tasks {
    public static final String TABLE_NAME = "tasks";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Essa anotacao manda o valor ser gerado automaticamente, diz q o valor é uma id (identity)
    @Column(name = "id", unique = true)
    private Long id;


    /*
     * Toda task tem que estar associada a um usuario. Várias tasks podem ser associadas ao mesmo usuário. Para especificar isso, usamos a 
     * annotation '@ManyToOne'
     *
     * 
     */
    // Usuário que pode realizar a task
    // Os usuarios terao listas de tasks possiveis
    // Cria uma colunade junção, ou chave estrangeira, para criar referencias entre vários objetos task e um
    // objeto user.
    @ManyToOne  // Anotacao que indica que vários objetos Task serao associadas um usuario
    // JoinColumn é usando para especificar as características da coluna de junção
    @JoinColumn(name = "user_id", nullable = false, updatable = false) // A coluna de referencias a objetos user
    // updatable diz se essa relacao com o objeto pode ser alterada
    private User user;

    // Descricao do que a task vai fazer
    @Column(name = "description", length = 250, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 1, max = 255)
    private String description;

    // Static -> é uma característica do tipo, não de um objeto

}
