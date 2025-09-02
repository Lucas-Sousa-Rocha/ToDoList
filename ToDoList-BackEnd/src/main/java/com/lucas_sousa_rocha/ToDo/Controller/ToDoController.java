package com.lucas_sousa_rocha.ToDo.Controller;

import com.lucas_sousa_rocha.ToDo.Model.ToDo;
import com.lucas_sousa_rocha.ToDo.Service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("todo")
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
    this.toDoService = toDoService;
    }

    @PostMapping
    public String salvarToDo(@RequestBody ToDo novoToDo) {
    try {
        this.toDoService.salvar(novoToDo);
        return "Cadastro ToDo realizado com sucesso!! " + novoToDo.getDescricao();
    }catch (IllegalArgumentException e) {
        var msgErro = e.getMessage();
        throw new ResponseStatusException(HttpStatus.CONFLICT, msgErro);
    }
    }

    @PutMapping("{id}")
    public void atualizarStatus(@PathVariable long id, @RequestBody ToDo novoToDo) {
    novoToDo.setId(id);
    this.toDoService.atualizarStatus(novoToDo);
    }

    @GetMapping
    public List<ToDo> listarTodos() {
        return toDoService.listarTodos();
    }

    @DeleteMapping("{id}")
    public String excluirToDo(@PathVariable Long id, ToDo novoToDo) {
    Optional<ToDo> optionalToDo = toDoService.buscarPorId(id);
    if (optionalToDo.isEmpty()) {
    return "Não foi possivel encontrar o ID";}
    String descricao = optionalToDo.get().getDescricao();
    novoToDo.setId(id);
    this.toDoService.excluirPorId(novoToDo.getId());
    return "ToDo Excluido com sucesso!! " + descricao;
    }
}
