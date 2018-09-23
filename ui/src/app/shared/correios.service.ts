import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Endereco} from '../core/model';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CorreiosService {
  url = environment.correios_url;
  constructor(
    private http: HttpClient
  ) { }

  getEndereco(cep: string) {
    const params = new HttpParams().append('app_key', environment.correios_key).append('app_secret', environment.correios_secret);
    return this.http.get<Endereco>(this.url + cep, {params: params});
  }
}
