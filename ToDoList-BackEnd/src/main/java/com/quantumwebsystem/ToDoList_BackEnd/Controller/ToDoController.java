package com.quantumwebsystem.ToDoList_BackEnd.Controller;

import com.quantumwebsystem.ToDoList_BackEnd.Model.ToDo;
import com.quantumwebsystem.ToDoList_BackEnd.Service.ToDoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> excluirToDo(@PathVariable Long id) {
        Optional<ToDo> optionalToDo = toDoService.buscarPorId(id);
        if (optionalToDo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("mensagem", "Não foi possível encontrar o ID: " + id));
        }
        ToDo todo = optionalToDo.get();
        toDoService.excluirPorId(id);
        return ResponseEntity.ok(
                Map.of(
                        "mensagem", "ToDo excluído com sucesso!",
                        "todo", todo
                )
        );
    }
}
