package com.quantumwebsystem.ToDoList_BackEnd.Service;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import com.quantumwebsystem.ToDoList_BackEnd.Repository.ToDoRepository;
import com.quantumwebsystem.ToDoList_BackEnd.Validator.ToDoValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public ToDo salvar(ToDo novoToDo){
        toDoValidator.validar(novoToDo);
        novoToDo.setDataCriacao(LocalDate.now());
        toDoRepository.save(novoToDo);
        return novoToDo;
    }

    //METODO EDITAR
    public void atualizarStatus(ToDo novoToDo){
        toDoRepository.save(novoToDo);
    }

    //LISTAR TODOS OS ToDoS
    public List<ToDo> listarTodos() {
        return toDoRepository.findAll();
    }

    public List<ToDo> listarEmAndamento(){
        return toDoRepository.findByConcluidoFalse();
    }

    public List<ToDo> listarConcluidos(){
        return toDoRepository.findByConcluidoTrue();
    }


    public List<ToDo> listarPorDataEConcluido(LocalDate data, boolean concluido){
        return toDoRepository.findByDataConclusaoAndConcluido(data, concluido);
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
