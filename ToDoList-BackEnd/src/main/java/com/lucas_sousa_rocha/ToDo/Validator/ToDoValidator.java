package com.lucas_sousa_rocha.ToDo.Validator;


import com.lucas_sousa_rocha.ToDo.Model.ToDo;
import com.lucas_sousa_rocha.ToDo.Repository.ToDoRepository;
import org.springframework.stereotype.Component;

@Component
public class ToDoValidator {
    private final ToDoRepository toDoRepository;

    public ToDoValidator(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public void validar(ToDo novoToDo){
    if (validaDescricao(novoToDo.getDescricao())){
        throw new IllegalArgumentException("Já existe um To Do Com Essa Descrição Cadastrado");
    }
    }

    public boolean validaDescricao(String descricao){
        return toDoRepository.existsByDescricao(descricao);
    }
}
