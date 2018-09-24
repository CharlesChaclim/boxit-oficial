import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {Cliente, ClienteEdit} from '../core/model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private url = `${environment.api_url}cliente/`;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  getAll() {
    return this.http.get(this.url);
  }

  excluir(id: string) {
    return this.http.delete(this.url + id);
  }

  getOne(id: string) {
    return this.http.get<ClienteEdit>(this.url + id);
  }

  emailExist(email: string) {
    return this.http.post(this.url + 'email' , {email});
  }

  cnpjExist(cnpj: string) {
    return this.http.post(this.url + 'cnpj' , {cnpj});
  }

  create(cliente: Cliente) {
    return this.http.post(this.url, JSON.stringify(cliente));
  }

  update(id: string, cliente: ClienteEdit) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + id, JSON.stringify(cliente), {headers: headers});
  }

}
