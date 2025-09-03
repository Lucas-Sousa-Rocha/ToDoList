import { Component, OnInit } from '@angular/core';
import { MainService } from './main.service';
import { Todo } from './todo';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-main',
  imports: [CommonModule, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {

  mostrarModal: boolean = false;

  constructor(private mainService: MainService) { }

  todos: Todo[] = [];
  novaDescricao: string = '';
  novoTodo = {
  descricao: this.novaDescricao,
  concluido: false // por padrão começa como não concluído
  };

  listarTodos() {
    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarTodos().subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data); // ✅ verifica resposta do backend
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err)
    });
  }

  listarEmAndamento() {
    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarEmAndamento().subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data); // ✅ verifica resposta do backend
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err)
    });
  }

  listarConcluidos() {
    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarConcluidos().subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data); // ✅ verifica resposta do backend
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err)
    });
  }

  abrirModal() {
    this.mostrarModal = true;
  }

  fecharModal() {
    this.mostrarModal = false;
  }

  salvarTodo() {
    this.mainService.criarTodo(this.novoTodo).subscribe({
      next: (res) => {
        console.log("Todo salvo com sucesso:", res);
        this.listarTodos(); // recarrega lista
        this.fecharModal(); // fecha modal
        this.novaDescricao = ""; // limpa campo
      },
      error: (err) => {
        console.error("Erro ao salvar todo:", err);
      }
    });
  }

  ngOnInit(): void {
    this.listarEmAndamento(); // chama automaticamente ao iniciar
  }

}
