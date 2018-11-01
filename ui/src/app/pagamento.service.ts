import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Pagamento} from './core/model';
import {environment} from '../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PagamentoService {
  private url = `${environment.api_url}pagamento/`;

  constructor(
    private http: HttpClient,
  ) { }

  getBoleto(numero: string) {
    return this.http.get<any>(this.url + numero);
  }


  update(pagamento: Pagamento) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.put(this.url, JSON.stringify(pagamento), {headers: headers});
  }

}
