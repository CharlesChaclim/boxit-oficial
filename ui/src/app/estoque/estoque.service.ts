import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Estoque, Page} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class EstoqueService {
  private url = `${environment.api_url}produto/`;

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

  filtrar(nome: string, categoria: string, enable: number, page: string, size: string) {
    let params = new HttpParams();
    params = params.append('nome', nome);
    params = params.append('categoria', categoria);
    params = params.append('enable', enable.toString());
    if (page) {
      page = (+page - 1).toString();
      params = params.append('page', page);
    }
    if (size) {
      params = params.append('size', size);
    }
    return this.http.get<Page>(this.url + 'filtrar', {params: params});
  }

  nomeExist(nome: string) {
    return this.http.post(this.url + 'nome', {nome});
  }

  skuExist(sku: string) {
    return this.http.post(this.url + 'sku', {sku});
  }

  criar(produto: Estoque) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.post(this.url, JSON.stringify(produto), {headers: headers});
  }

  updateEnable(id: string, enabled: boolean) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + id + '/enable', {enabled}, {headers: headers});
  }

  getOne(id: string) {
    return this.http.get<Estoque>(this.url + id);
  }

  update(produto: Estoque) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + produto['id'], JSON.stringify(produto), {headers: headers});
  }
}
