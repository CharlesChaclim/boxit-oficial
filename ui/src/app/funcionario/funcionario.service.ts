import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Page} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {
  private url = `${environment.api_url}funcionario/`;
  constructor(
    private http: HttpClient
  ) { }

  listAll() {
    return this.http.get<Page>(this.url);
  }

  filtrar(name: string, cpf: string, email: string) {
    let params = new HttpParams();
    params = params.append('nome', name);
    params = params.append('cpf', cpf);
    params = params.append('email', email);
    return this.http.get<Page>(this.url + 'filter', {params: params});
  }
}
