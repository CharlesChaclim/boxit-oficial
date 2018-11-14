import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Relatorio} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class RelatorioService {
  private url = `${environment.api_url}relatorio/`;

  constructor(
    private http: HttpClient,
  ) { }

  geraRelatorio(relatorio: Relatorio) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url, JSON.stringify(relatorio), {headers: headers});
  }
}
