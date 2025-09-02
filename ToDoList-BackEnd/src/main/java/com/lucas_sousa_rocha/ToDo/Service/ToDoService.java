package com.lucas_sousa_rocha.ToDo.Service;

import com.lucas_sousa_rocha.ToDo.Model.ToDo;
import com.lucas_sousa_rocha.ToDo.Repository.ToDoRepository;
import com.lucas_sousa_rocha.ToDo.Validator.ToDoValidator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ToDoService {

    //INJEÇÃO DE DEPÊNDENCIA
    private final ToDoRepository toDoRepository;
    private final ToDoValidator toDoValidator;

    //CONSTRUTOR DA INJEÇÃO DE DEPÊNDENCIA
    public ToDoService(ToDoRepository toDoRepository, ToDoValidator toDoValidator) {
        this.toDoRepository = toDoRepository;
        this.toDoValidator = toDoValidator;
    }

    // METODO SALVAR
    public void salvar(ToDo novoToDo){
        toDoValidator.validar(novoToDo);
        toDoRepository.save(novoToDo);
    }

    //METODO EDITAR
    public void atualizarStatus(ToDo novoToDo){
        toDoRepository.save(novoToDo);
    }

    //LISTAR TO DOS OS TODOS
    public List<ToDo> listarTodos() {
        return toDoRepository.findAll();
    }

    //EXCLUIR TO DO POR ID
    public void excluirPorId(Long id){
        toDoRepository.deleteById(id);
    }

    //LOCALIZA POR ID
    public Optional<ToDo> buscarPorId(Long id) {
        return toDoRepository.findById(id);
    }

}
