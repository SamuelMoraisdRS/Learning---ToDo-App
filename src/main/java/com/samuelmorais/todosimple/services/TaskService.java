package com.samuelmorais.todosimple.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.samuelmorais.todosimple.models.Tasks;
import com.samuelmorais.todosimple.models.User;
import com.samuelmorais.todosimple.repositories.TasksRepository;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    // IUmporta as annotation do declaracao do tipo    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TasksRepository tasksRepository;

    public Tasks findByID(Long id) {
        // a interface repository acessa o sbanco de dados SQL diretamente
        // Service realiza as acoes ligadas às estruturas de dados (model) e  ao banco (repository)
        Optional<Tasks> obj = tasksRepository.findById(id);
        return obj.orElseThrow();
    }

    public List<Tasks> findAllByUserId(Long user_id) {
        return tasksRepository.findByUser_Id(user_id);
    }

    @Transactional
    public Tasks create(Tasks obj) {
        // checa se o usuario associado a task existe. O user associado à task é definido na própria task
        // se nao achar, joga a excecao
        User user = userService.findByID(obj.getUser().getId());
        // Para criar um novo objeto no banco de dados, pois não haverá nenhum elemento com id null no banco,
        // logo, o método save não encontrará um objeto e criará um novo
        obj.setId(null);
        obj.setUser(user);
        // O método save, caso não haja objeto no banco de dados com a id inserida, criará um novo objeto no banco de dados
        // caso haja algum objeto com a id, o método fará a atualizacao dos dados
        tasksRepository.save(obj);
        return obj; 
    }
    @Transactional
    public Tasks update(Tasks obj) {
        // Se o objeot nao existir no banco de dados, a exceção encerrará a operacao
        Tasks newObj = findByID(obj.getId());
        newObj.setDescription(obj.getDescription());
        // O método save serve pra persistir as mudancas feitas no banco de dados (Salvar as mudancas)
        tasksRepository.save(newObj); 
        return newObj;
    }
    @Transactional
    public void delete(Long id) {
        // Caso não haja objeto com este id, retornará 
        Tasks obj = findByID(id);
        // Precisamos do try catch pois se houverem entidades relacionadas ao elemento que queremos deletar, haverá erro
        try {
            this.tasksRepository.delete(obj);
        } catch (Exception e) {
            throw new RuntimeException("Não é possível apagar pois há entidades relacionadas");    
        }
    }
}
