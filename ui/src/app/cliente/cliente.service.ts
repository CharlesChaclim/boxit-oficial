import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Cliente, ClienteEdit, Page} from '../core/model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {
  private url = `${environment.api_url}cliente/`;
  private authUrl = `${environment.api_url}auth/`;

  constructor(
    private http: HttpClient,
    private router: Router
  ) { }

  getAll() {
    return this.http.get(this.url);
  }

  atualizarEnable(id: string, enabled: boolean) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + id + '/enable', {enabled}, {headers: headers});
  }

  filtrar(name: string, cnpj: string, nomeFantasia: string, enable: number, pagina: string, tamanho: string) {
    let params = new HttpParams();
    params = params.append('nome', name);
    params = params.append('cnpj', cnpj);
    params = params.append('nomeFantasia', nomeFantasia);
    params = params.append('enable', enable.toString());
    if (pagina) {
      pagina = (+ pagina - 1).toString();
      params = params.append('page', pagina);
    }
    if (tamanho) {
      params = params.append('size', tamanho);
    }
    return this.http.get<Page>(this.url + 'filtro', {params: params});
  }

  getOne(id: string) {
    return this.http.get<ClienteEdit>(this.url + id);
  }

  emailExist(email: string) {
    return this.http.post(this.authUrl + 'email' , {email});
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
