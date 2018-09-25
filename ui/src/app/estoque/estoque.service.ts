import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Estoque, Page} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class EstoqueService {
  private url = `${environment.api_url}estoque/`;

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

  filtrar(nome: string, cpf: string, email: string, page: string, size: string) {
    let params = new HttpParams();
    params = params.append('nome', nome);
    params = params.append('cpf', cpf);
    params = params.append('email', email);
    if (page) {
      page = (+page - 1).toString();
      params = params.append('page', page);
    }
    if (size) {
      params = params.append('size', size);
    }
    return this.http.get<Page>(this.url + 'filtrar', {params: params});
  }

  criar(produto: Estoque) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.post(this.url, JSON.stringify(produto), {headers: headers});
  }

  deletar(id: string) {
    return this.http.delete(this.url + id);
  }

  updateEnable(id: string, enabled: boolean) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + id + '/enabled', {enabled}, {headers: headers});
  }

  getOne(id: string) {
    return this.http.get<Estoque>(this.url + id);
  }

  update(produto: Estoque) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + produto['id'], JSON.stringify(produto), {headers: headers});
  }
}
