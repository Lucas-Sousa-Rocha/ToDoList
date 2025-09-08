import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Todo } from './todo';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class MainService {
  private API_PATH = environment.API_PATH;

  constructor(private httpclient: HttpClient) { }

  listarPorDataEConcluido(concluido: boolean, dataCriacao: Date): Observable<Todo[]> {
    const dataFormatada = dataCriacao.toISOString().split('T')[0];
    return this.httpclient.get<Todo[]>(
      `${this.API_PATH}todo/data-e-concluido`,
      {
        params: {
          data: dataFormatada,
          concluido: concluido.toString(),
        },
      }
    );
  }

  listarTodosPorData(dataCriacao: Date): Observable<Todo[]> {
    const dataFormatada = dataCriacao.toISOString().split('T')[0];
    return this.httpclient.get<Todo[]>(`${this.API_PATH}todo/listar-por-data`, {
      params: {
        data: dataFormatada,
      },
    });
  }

  criarTodo(todo: Todo): Observable<Todo> {
    return this.httpclient.post<Todo>(`${this.API_PATH}todo`, todo);
  }

  excluirToDo(id: number): Observable<{ mensagem: string; todo: Todo }> {
    return this.httpclient.delete<{ mensagem: string; todo: Todo }>(
      `${this.API_PATH}todo/${id}`
    );
  }

  atualizarToDo(id: number, todo: Todo): Observable<Todo> {
    return this.httpclient.put<Todo>(`${this.API_PATH}todo/${id}`, todo);
  }

}
