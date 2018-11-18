import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Page} from '../core/model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PedidoService {
  private url = `${environment.api_url}pedido/`;
  private url2 = `${environment.api_url}pagamento/`;

  constructor(
    private http: HttpClient,
  ) { }

  listCnpj(page: string, size: string, cnpj: string) {
    let params = new HttpParams();
    if (page) {
      page = (+page - 1).toString();
      params = params.append('page', page);
    }
    if (size) {
      params = params.append('size', size);
    }
    params = params.append('cnpj', cnpj);
    return this.http.get<Page>(this.url, {params: params});
  }

  listPedido() {
    return this.http.get<Page>(this.url + 'pedido');
  }

  listPagamento() {
    return this.http.get<Page>(this.url2);
  }
}
