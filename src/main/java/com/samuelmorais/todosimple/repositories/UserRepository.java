package com.samuelmorais.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.samuelmorais.todosimple.models.User;

/*
 * Fará a comunicacao daaplicacao com o banco de dados. Irá gerenciar os querys feitos ao SQL.
 * O arquivo repository serve para conter os metodos relacionadas aos pedidos feitos num banco de dados. Ou seja
 * inclui metodos relacionados a busca de dados, exclusao, retorno etc.
 * O framework spring ja contem em sua biblioteca a interface JpaRepository, que já contem em seu código TODOS
 * os métodos relacionados ao query necessário. Logo, não há necessidade de escrever o código para o elemto 
 * repository  
 * 
 * ESSA É UMA FORMA E AUTOMATIZAR AS CHAMADAS AO BANCO DE DADOS
 */

 
public interface UserRepository extends JpaRepository<User,Long> {
    
}
