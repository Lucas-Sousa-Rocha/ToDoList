package com.quantumwebsystem.ToDoList_BackEnd.Repository;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    boolean existsByDescricao(String descricao);


    List<ToDo> findByConcluidoFalse();

    List<ToDo> findByConcluidoTrue();
}
