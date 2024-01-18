package com.samuelmorais.todosimple.models;
// Model é a parte q processa e organiza os dados, vamos declarar os objetos do user


// Persistence -> a possibilidade de manter os dados atraves das diversas execuções do programa
// A JPA (Java Persistence API) é uma biblioteca que fornece mecanismos para gerenciar os dados
// abrangidos pelas persistencias
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.OverridesAttribute.List;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
@Table(name = User.TABLE_NAME) // Esta anotação irá declarar as características da tabela 
public class User {
    // Conterá os métodos associados à criação do user e da atualizaçao do user
    public interface CreateUser {};
    public interface UpdateUser {};
    
    
    // O tipo final é o mesmo de const
    // O termo Static implica que o membro/método é uma caracteristica da CLASSE nao de uma INSTANCIA da classe
    // Logo, podemos usa-los sem instanciar um objeto especifico.
    public static final String TABLE_NAME = "user";

    // int é o tipo primitivo da linguagem, Integer é a classe na biblioteca padrao, logo, temos mais metodos
    // em c++, int = int e Integer = std::int
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    /*
     * Length = tamanho maximo dos elementos da tabela (tamanho maximo do username)
     * nullable = Se aceita valores nulos na tabela ou não(no caso, não pode haver um username vazio)
     * unique = Se cada elemento da tabela deve ser unico (no caso, não podemos ter dois usuarios com mesmo username)
     */
    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = CreateUser.class)
    
    @Size(groups = CreateUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY)   // Não retorna a senha para o frontend em momento algum, será permitida apenas a escrita da senha
    @Column(name = "password", length = 60, nullable = false)
    @NotNull(groups = {CreateUser.class, UpdateUser.class})
    @Size(groups = {CreateUser.class, UpdateUser.class}, min = 8, max = 100)
    private String password;

    // O spring precisa q a classe tenha um construtor vazio
    public User() {
    }
    // Esse é o construtor q vai gerar o objeto usuario
    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getters e setters (necessarios para a organizacao do bean)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Definimos nossa propria checagem de igual
    /*  == vs .equals
            O operador == checa a alocação de memória, ou seja, ele retorna true se ambas as variaveis apontarem para o mesmo
            local da memória. Ou seja, caso haja dois objetos em locacoes diferentes da memoria heap com os mesmos conteudos, o operador
            == retornará FALSE.
            Por outro lado, o metodo genérico equals checara o CONTEÚDO dos objetos, independente de ambos compartilharem o mesmo segmento
            de memória, por isso, a implementação de equals é mais complexa.
     */
    @Override   // Anotaçao que informa que a classe abaixo é uma sobrecarga da classe da biblioteca padrão
    public boolean equals (Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof User)){    // Se 'o' NÃO for instancia da classe
            return false;
        }
        User user = (User) o;
        return Objects.equals(user.id, id) && Objects.equals(user.username,username) &&
                Objects.equals(user.password, password);
    }

    @Override   // Código para a chave hash
    public int hashCode() {
        final int prime = 57;
        int result = prime + (id == null ? 0 : id.hashCode());
        return result;
    }



    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", username='" + getUsername() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

    
    // Um user para varias tasks
    @OneToMany(mappedBy = "user")
    private java.util.List<Tasks> taskList = new ArrayList<Tasks>();
    
}
