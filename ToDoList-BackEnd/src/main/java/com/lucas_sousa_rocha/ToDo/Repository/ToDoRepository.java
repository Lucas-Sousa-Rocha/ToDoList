package com.lucas_sousa_rocha.ToDo.Repository;

import com.lucas_sousa_rocha.ToDo.Model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo,Long> {
    boolean existsByDescricao(String descricao);


}
