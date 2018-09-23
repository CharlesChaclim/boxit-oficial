import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Cliente} from '../core/model';
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
    return this.http.get<Cliente>(this.url + id);
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

  update(id: string, cliente: Cliente) {
    return this.http.put(this.url + id, JSON.stringify(cliente));
  }

}
