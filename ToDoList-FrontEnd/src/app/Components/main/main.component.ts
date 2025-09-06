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

  ngOnInit(): void {
    this.listarEmAndamentoPorData(); // chama automaticamente ao iniciar
  }

  abrirModal() {
    this.mostrarModal = true;
  }

  fecharModal() {
    this.mostrarModal = false;
  }

  // componente.ts
  dataFiltro: string ;
  
  constructor(private mainService: MainService) { 
    const hoje = new Date();
    const ano = hoje.getFullYear();
    const mes = String(hoje.getMonth() + 1).padStart(2, '0');
    const dia = String(hoje.getDate()).padStart(2, '0');
    // monta no formato YYYY-MM-DD (aceito pelo input date)
    this.dataFiltro = `${ano}-${mes}-${dia}`;
  }

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

    const dataConvertida = new Date(this.dataFiltro);

    console.log('listarTodos chamado'); // ✅ verifica se a função roda
    this.mainService.listarPorDataEConcluido(false, dataConvertida).subscribe({
      next: (data) => {
        console.log('Dados recebidos:', data); // ✅ verifica resposta do backend
        console.log('Listar Em Andamento Por Data');
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
        console.log('Dados recebidos:', data); // ✅ verifica resposta do backend
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
        this.ngOnInit();
        this.fecharModal(); // fecha modal
        this.novoTodo.descricao = ''; // limpa campo
        this.novoTodo.concluido = false; // reseta status
      },
      error: (err) => {
        console.error('Erro ao salvar todo:', err);
      },
    });
  }

  excluirToDo(id: number): void {
    if (!id) return;
    if (confirm('Tem certeza que deseja excluir este ToDo?')) {
      this.mainService.excluirToDo(id).subscribe({
        next: (res) => {
          alert(res.mensagem);
          this.todos = this.todos.filter((todo) => todo.id !== id);
        },
        error: (err) => console.error('Erro ao excluir:', err),
      });
    }
  }

  marcarConcluido(todo: Todo): void {
    this.mainService.atualizarToDo(todo.id, todo).subscribe({
      next: (todoAtualizado: Todo) => {
        // atualiza o item na lista com os dados que vieram do backend
        const index = this.todos.findIndex(t => t.id === todo.id);
        if (index !== -1) {
          this.todos[index] = todoAtualizado; // substitui o item antigo
        }
        console.log('Status atualizado com sucesso');
        this.ngOnInit(); // atualiza a lista
      },
      error: err => {
        console.error('Erro ao atualizar status', err);
      }
    });
  }




}
