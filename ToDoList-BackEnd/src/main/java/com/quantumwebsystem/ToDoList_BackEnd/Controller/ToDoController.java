package com.quantumwebsystem.ToDoList_BackEnd.Controller;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import com.quantumwebsystem.ToDoList_BackEnd.Service.ToDoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.function.ServerRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("todo")
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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

    @GetMapping("data-e-concluido")
    public List<ToDo> listarPorDataEConluido(@RequestParam(required = false) LocalDate data, @RequestParam(required = false) boolean concluido){
        return toDoService.listarPorDataEConcluido(data, concluido);
    }

    @GetMapping("listar-por-data")
    public List<ToDo> listarTodosPorData(@RequestParam(required = false) LocalDate data){
        return toDoService.listarPorData(data);
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
