import { Component } from '@angular/core';
import { MainService } from './main.service';
import { Todo } from './todo';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-main',
  imports: [CommonModule],
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent {

  constructor(private mainService: MainService){}

  todos: Todo[] = [];

  /*listarTodos(){
    this.mainService.listarTodos()
    .then(todo => console.log(todo))
    .catch(todo => console.log(todo))
  }*/

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

}
