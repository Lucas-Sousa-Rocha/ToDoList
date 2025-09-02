package com.quantumwebsystem.ToDoList_BackEnd.Repository;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    boolean existsByDescricao(String descricao);


}
