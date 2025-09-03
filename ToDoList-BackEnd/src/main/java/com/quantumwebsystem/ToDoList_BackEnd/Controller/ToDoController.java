package com.quantumwebsystem.ToDoList_BackEnd.Controller;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import com.quantumwebsystem.ToDoList_BackEnd.Service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("todo")
@CrossOrigin(origins = "http://localhost:4200/", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ToDoController {

    private final ToDoService toDoService;

    public ToDoController(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    @PostMapping
    public ResponseEntity<ToDo> salvarToDo(@RequestBody ToDo novoToDo) {
        try {
            ToDo todoSalvo = this.toDoService.salvar(novoToDo); // retorna o ToDo salvo
            return ResponseEntity.ok(todoSalvo); // envia o objeto como JSON
        } catch (IllegalArgumentException e) {
            String msgErro = e.getMessage();
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

    @GetMapping("em-andamento")
    public List<ToDo> listarEmAndamento(){
        return toDoService.listarEmAndamento();
    }

    @GetMapping("concluidos")
    public List<ToDo> listarConcluidos(){
        return toDoService.listarConcluidos();
    }

    @GetMapping("data-e-conclusao")
    public List<ToDo> listarPorDataEConcluido(@RequestParam(required = false) LocalDate data, @RequestParam(required = false) boolean concluido){
        return toDoService.listarPorDataEConcluido(data, concluido);
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
