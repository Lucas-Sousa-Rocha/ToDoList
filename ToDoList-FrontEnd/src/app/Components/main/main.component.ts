import { Component, OnInit } from '@angular/core';
import { MainService } from './main.service';
import { Todo } from './todo';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-main',
  imports: [CommonModule, FormsModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
})
export class MainComponent implements OnInit {
  mostrarModal: boolean = false;

  dataFiltro: string = new Date().toISOString().split('T')[0]; 

  constructor(private mainService: MainService) {}

  todos: Todo[] = [];
  novaDescricao: string = '';
  novoTodo = {
    descricao: this.novaDescricao,
    concluido: false, 
  };
  
  listarEmAndamentoPorData() {
    if (!this.dataFiltro) {
      console.warn('Selecione uma data!');
      return;
    }
    // converte string para Date
    const dataConvertida = new Date(this.dataFiltro);

    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarPorDataEConcluido(false, dataConvertida).subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data ); // ✅ verifica resposta do backend
        console.log("Listar Em Andamento Por Data")
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err),
    });
  }

  listarTodosPorData() {
    if (!this.dataFiltro) {
      console.warn('Selecione uma data!');
      return;
    }
    // converte string para Date
    const dataConvertida = new Date(this.dataFiltro);
    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarTodosPorData(dataConvertida).subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data ); // ✅ verifica resposta do backend
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err),
    });
  }

  listarConcluidosPorData() {
    if (!this.dataFiltro) {
      console.warn('Selecione uma data!');
      return;
    }
    // converte string para Date
    const dataConvertida = new Date(this.dataFiltro);
    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarPorDataEConcluido(true, dataConvertida).subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data); // ✅ verifica resposta do backend
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err),
    });
  }

  salvarTodo() {
    this.mainService.criarTodo(this.novoTodo).subscribe({
      next: (res) => {
        console.log('Todo salvo com sucesso:', res);
        this.ngOnInit()
        this.fecharModal(); // fecha modal
        this.novoTodo.descricao = ''; // limpa campo
        this.novoTodo.concluido = false; // reseta status
      },
      error: (err) => {
        console.error('Erro ao salvar todo:', err);
      },
    });
  }

  ngOnInit(): void {
    this.listarEmAndamentoPorData(); // chama automaticamente ao iniciar
  }

  abrirModal() {
    this.mostrarModal = true;
  }

  fecharModal() {
    this.mostrarModal = false;
  }
}
