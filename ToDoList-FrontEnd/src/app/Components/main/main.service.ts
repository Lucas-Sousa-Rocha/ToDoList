import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Todo } from './todo';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MainService {

  private API_PATH = environment.API_PATH; // ex: 'http://localhost:8080/api/'

  constructor(private httpclient: HttpClient) { }

  listarTodos(): Observable<Todo[]> {
    return this.httpclient.get<Todo[]>(`${this.API_PATH}todo`);
  }

  listarEmAndamento(): Observable<Todo[]> {
    return this.httpclient.get<Todo[]>(`${this.API_PATH}todo/em-andamento`);
  }
}
