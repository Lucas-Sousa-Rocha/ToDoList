package com.quantumwebsystem.ToDoList_BackEnd.Repository;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {

    boolean existsByDescricao(String descricao);

    List<ToDo> findByDataCriacaoAndConcluido(LocalDate dataCriacao, Boolean concluido);

    List<ToDo> findByDataCriacao(LocalDate dataCriacao);
}
