package com.samuelmorais.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samuelmorais.todosimple.models.Tasks;
import java.util.List;


/*
 * Springboot -> Extensao que faz a configuracao do ambiente spring de forma automatica (cria os arquivos e 
 * elementos necessarios)
 */

/*
 * Para aprender, faremos nosso próprio código para a query.
 * Podemos usar o JPQL. Uma forma de mesclar a escrita em SQl com algumas automacoes do spring
 */

@Repository
public interface TasksRepository extends JpaRepository<Tasks,Long> {
    
    List<Tasks> findByUser_Id(Long id);
    
    // Usando o JPA, quando criamos uma classe, estamos criando uma tabela (entity no SQL)
    // A anotacao query contem o código da requisicao que será feita
    // chamaremos o objeto a partir da tabela Tasks (classes sao entidades, ou seja, tabelas)
    // value é valor que será extraido da tabela com o jpl
    // nativeQuery informa que utilizaremos a linguagem do sql
    // tasks esta me mminusculo pois esse é o nome da tabela -> estamos acessando o banco de dados, logo, usamos
    // os nomes dele
    // @Query(value = "SELECT * FROM tasks WHERE t.user.id = :id", nativeQuery = true)
    // Tasks findByUserId(@Param("id") Long user_id);
}
