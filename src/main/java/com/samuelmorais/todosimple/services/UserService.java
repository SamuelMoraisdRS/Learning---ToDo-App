package com.samuelmorais.todosimple.services;

import com.samuelmorais.todosimple.repositories.UserRepository;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.samuelmorais.todosimple.models.User;
import com.samuelmorais.todosimple.repositories.TasksRepository;

/*
 * O arquivo repository fará requisicoes no SQL. No entanto, não há uma conexão direta com o aqruivo model. Ou seja
 * , o arquivo repository não tem comunicação com a camada model do código. Para manter a modularidade do código'
 * criaremos o arquivo service, que cuidará dos pedidos de requisicao ao banco de dados presentes na camada model.
 *
 */

public class UserService {
    /*
     * Instanciamos obejtos das interfaces para podermos utilizar seus métodos
     * 
     * Autowired é uma anotacao que informa ao compilador que ele deve importar para este objeto as anotacoes 
     * presentes na sua declaracao. Logo, o compilador sabera q trata-se de um repository e não pedirá um construtor
     */
    @Autowired
    static private UserRepository userRepository;
    @Autowired
    static private TasksRepository taskRepository;

    /*model contem as estruturas de dados!!!!! somente, servieces tem as operacoes */

    // Esse método retornará uma exceção se nao encontrar o usuario
    public User findByID(Long id) {
        /*
         * Ao utilizar as buscas de dados no banco, deveremos usar o tipo optional. Pois sempre há a possibilidade
         * do objeto procurado não estar registrado no banco. Logo, neste caso, devemos retornar o valor null.
         */
        Optional<User> userOptional = userRepository.findById(id);
        // O metodo orElse pertence à classe optional, ele retorna o objeto caso o optional não for nulo, ou
        // um optional vazio caso seja. O optional n retorna nulo, ele retorna um objeto optional vazio
        return userOptional.orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
    }
    // Essa anotacao deve ser usada sempre que um dado for salvo no banco
    // Garnate que todos os dados sejam salvos. Essa anotacao é custosa, pois requer uma checagem do banco inteiro,
    // logo, deve ser usada apenas nas insercoes no banco
    @Transactional
    public User create(User object) {
        object.setId(null);
        object = userRepository.save(object);
        // taskRepository é uma interface, ele tem apenas métodos
        taskRepository.saveAll(object.getTasks());
        return object;
    }

    // Ao fazer o update, devemos observar q nem todas as informacoes podem ser atualizadas, por exemplo,
    // nao podemos, mudar o username do user, logo
    @Transactional
    public User update(User object) {
        User newUser = findByID(object.getId());
        newUser.setPassword(object.getPassword());
        return userRepository.save(newUser);

    }

    @Transactional
    public void delete(Long id) {
        // Se nao encontrar o usuario, irá retornar a runtimeexception
        findByID(id);
        // O erro que pode acontecer é o dados ter relacoes dentro do banco
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            // A excecao sera aquela do caso da entidade estar relacionada a alguma outra entidade
            throw new RuntimeException("Usuário não encontrado. " + "Id: " + id + "\n");
        }
        
    }

}  
