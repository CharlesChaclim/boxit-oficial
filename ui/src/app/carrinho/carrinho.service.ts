import {Injectable} from '@angular/core';
import {environment} from '../../environments/environment';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Carrinho, Cart, Estoque} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class CarrinhoService {
  private url = `${environment.api_url}carrinho/`;

  constructor(
    private http: HttpClient
  ) { }

  getOne(id: string) {
    return this.http.get<Cart>(this.url + id);
  }

  add(cart: Carrinho) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.post(this.url , JSON.stringify(cart), {headers: headers});
  }

  remove(id: string) {
    return this.http.delete(this.url + id);
  }
}
