package com.quantumwebsystem.ToDoList_BackEnd.Service;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import com.quantumwebsystem.ToDoList_BackEnd.Repository.ToDoRepository;
import com.quantumwebsystem.ToDoList_BackEnd.Validator.ToDoValidator;
import jakarta.transaction.Transactional;
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
    @Transactional
    public ToDo atualizarStatus(ToDo novoToDo){
        Optional<ToDo> existente = toDoRepository.findById(novoToDo.getId());
        if (existente.isPresent()) {
            ToDo t = existente.get();
            t.setConcluido(true); // atualiza o status
            t.setDataConclusao(LocalDate.now()); // define a data de conclusão
            return toDoRepository.save(t); // persiste as mudanças
        } else {
            throw new IllegalArgumentException("ToDo não encontrado com id: " + novoToDo.getId());
        }
    }


    public List<ToDo> listarPorDataEConcluido(LocalDate data, boolean concluido){
        return toDoRepository.findByDataCriacaoAndConcluido(data, concluido);
    }

    public List<ToDo> listarPorData(LocalDate data){
        return toDoRepository.findByDataCriacao(data);
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
