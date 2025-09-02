package com.quantumwebsystem.ToDoList_BackEnd.Validator;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import com.quantumwebsystem.ToDoList_BackEnd.Repository.ToDoRepository;
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
