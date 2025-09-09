import { Component, OnInit } from '@angular/core';
import { MainService } from './main.service';
import { Todo } from './todo';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DatePickerModule } from 'primeng/datepicker';
import { ButtonModule } from 'primeng/button';
import { ButtonGroupModule } from 'primeng/buttongroup';
import { CardModule } from 'primeng/card';
import { DialogModule } from 'primeng/dialog';
import { CascadeSelectModule } from 'primeng/cascadeselect';

@Component({
  selector: 'app-main',
  imports: [
    CommonModule,
    FormsModule,
    DatePickerModule,
    ButtonModule,
    ButtonGroupModule,
    CardModule,
    DialogModule,
    CascadeSelectModule
  ],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css',
})
export class MainComponent implements OnInit {
  visible: boolean = false;

  mostrarModal: boolean = false;

  popupMensagem: string | null = null;

  dataFiltro: Date;

  novoToDo: any;

  todos: Todo[] = [];

  constructor(private mainService: MainService) {
    const hoje = new Date();
    this.dataFiltro = hoje;
    this.novoToDo = {
      descricao: '',
      concluido: false,
      dataCriacao: this.dataFiltro,
      dataConclusao: null,
    };
  }

  showDialog() {
    this.visible = true;
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

  mostrarPopup(mensagem: string, tipo: 'sucesso' | 'erro') {
    this.popupMensagem = mensagem;

    // muda a cor do popup de acordo com o tipo
    const popupDiv = document.querySelector('.popup') as HTMLElement;
    if (popupDiv) {
      popupDiv.style.backgroundColor =
        tipo === 'sucesso' ? '#d4edda' : '#f8d7da';
      popupDiv.style.color = tipo === 'sucesso' ? '#155724' : '#721c24';
    }

    // desaparece automaticamente após 3 segundos
    setTimeout(() => (this.popupMensagem = null), 5000);
  }

  fecharPopup() {
    this.popupMensagem = null;
  }

  listarEmAndamentoPorData() {
    if (!this.dataFiltro) {
      console.warn('Selecione uma data!');
      return;
    }
    const dataConvertida = new Date(this.dataFiltro);
    this.mainService.listarPorDataEConcluido(false, dataConvertida).subscribe({
      next: (data) => {
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err),
    });
  }

  listarTodosPorData() {
    const dataConvertida = new Date(this.dataFiltro);
    this.mainService.listarTodosPorData(dataConvertida).subscribe({
      next: (data) => {
        this.todos = data;
      },
    });
  }

  listarConcluidosPorData() {
    if (!this.dataFiltro) {
      console.warn('Selecione uma data!');
      return;
    }
    const dataConvertida = new Date(this.dataFiltro);
    this.mainService.listarPorDataEConcluido(true, dataConvertida).subscribe({
      next: (data) => {
        this.todos = data;
      },
      error: (err) => console.error('Erro ao listar todos:', err),
    });
  }

  salvarTodo() {
    this.mainService.criarTodo(this.novoToDo).subscribe({
      next: (res) => {
        this.ngOnInit();
        this.visible = false
        this.novoToDo.descricao = ''; // limpa campo
        this.novoToDo.concluido = false; // reseta status
      },
      error: (err) => {
        if (err.status === 409) {
          this.mostrarPopup(
            'Já existe um ToDo com essa descrição nesta data!',
            'erro'
          );
        } else {
          this.mostrarPopup('Erro ao salvar todo. Tente novamente.', 'erro');
        }
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
        const index = this.todos.findIndex((t) => t.id === todo.id);
        if (index !== -1) {
          this.todos[index] = todoAtualizado; // substitui o item antigo
        }
        console.log('Status atualizado com sucesso');
        this.ngOnInit(); // atualiza a lista
      },
      error: (err) => {
        console.error('Erro ao atualizar status', err);
      },
    });
  }
}
