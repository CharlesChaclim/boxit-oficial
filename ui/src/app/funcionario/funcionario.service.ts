import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Funcionario, Page} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {
  private url = `${environment.api_url}funcionario/`;
  private authUrl = `${environment.api_url}auth/`;
  constructor(
    private http: HttpClient
  ) { }

  listAll(page: string, size: string) {
    let params = new HttpParams();
    if (page) {
      page = (+page - 1).toString();
      params = params.append('page', page);
    }
    if (size) {
      params = params.append('size', size);
    }
    return this.http.get<Page>(this.url, {params: params});
  }

  filtrar(name: string, cpf: string, email: string, page: string, size: string) {
    let params = new HttpParams();
    params = params.append('nome', name);
    params = params.append('cpf', cpf);
    params = params.append('email', email);
    if (page) {
      page = (+page - 1).toString();
      params = params.append('page', page);
    }
    if (size) {
      params = params.append('size', size);
    }
    return this.http.get<Page>(this.url + 'filter', {params: params});
  }

  emailExist(email: string) {
    return this.http.post(this.authUrl + 'email', {email});
  }

  usernameExist(username: string) {
    return this.http.post(this.authUrl + 'username', {username});
  }

  cpfExist(cpf: string) {
    return this.http.post(this.url + 'cpf', {cpf});
  }

  create(funcionario: Funcionario) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.post(this.url, JSON.stringify(funcionario), {headers: headers});
  }

  delete(id: string) {
    return this.http.delete(this.url + id);
  }

  updateEnable(id: string, enabled: boolean) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.authUrl + id + '/enabled', {enabled}, {headers: headers});
  }
}
