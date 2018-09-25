import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Categoria, Page} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private url = `${environment.api_url}categoria/`;

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

  nomeExist(nome: string) {
    return this.http.post(this.url + 'nome', {nome});
  }

  criar(item: Categoria) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.post(this.url, JSON.stringify(item), {headers: headers});
  }

  deletar(id: string) {
    return this.http.delete(this.url + id);
  }

  updateEnable(id: string, enabled: boolean) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + id + '/enable', {enabled}, {headers: headers});
  }

  getOne(id: string) {
    return this.http.get<Categoria>(this.url + id);
  }

  update(item: Categoria) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url + item['id'], JSON.stringify(item), {headers: headers});
  }
}
