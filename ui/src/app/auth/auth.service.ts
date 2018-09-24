import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {JwtHelperService} from '@auth0/angular-jwt';
import {environment} from '../../environments/environment';
import {Cliente} from '../core/model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  oauthTokenUrl: string;
  jwtPayload: any;

  constructor(
    private http: HttpClient,
    private jwtHelper: JwtHelperService
  ) {
    this.oauthTokenUrl = `${environment.api_url}oauth/token`;
    this.carregarToken();
  }

  login(usuario: string, senha: string): Promise<void> {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/x-www-form-urlencoded')
      .append('Authorization', 'Basic YW5ndWxhcjpAbmd1bEByMA==');

    const body = `username=${usuario}&password=${senha}&grant_type=password`;

    return this.http.post<any>(this.oauthTokenUrl, body,
      { headers, withCredentials: true})
      .toPromise()
      .then(response => {
        this.armazenarToken(response.access_token);
      })
      .catch(response => {
        if (response.status === 400) {
          if (response.error.error === 'invalid_grant') {
            return Promise.reject('Usuário ou senha inválida!');
          }
        }
        return Promise.reject(response);
      });
  }

  create(cliente: Cliente) {
    const headers = new HttpHeaders().set('Content-type', 'application/json');
    return this.http.post<any>(`${environment.api_url}cliente`, JSON.stringify(cliente)
      , {headers: headers})
      .toPromise()
      .then()
      .catch(response => {
        return Promise.reject(response);
      });
  }

  confirmar(codigo: string): Promise<void> {
    const queryParams = new HttpParams().set('code', codigo);
    return this.http.post<any>(`${environment.api_url}auth/confirmation`, {}, {params: queryParams})
      .toPromise()
      .then()
      .catch(err => Promise.reject(err));
  }

  obterNovoAccessToken(): Promise<void> {
    const headers = new HttpHeaders()
      .append('Content-Type', 'application/x-www-form-urlencoded')
      .append('Authorization', 'Basic YW5ndWxhcjpAbmd1bEByMA==');

    const body = 'grant_type=refresh_token';

    return this.http.post<any>(this.oauthTokenUrl, body,
      { headers, withCredentials: true})
      .toPromise()
      .then(response => {
        this.armazenarToken(response.access_token);
        return Promise.resolve(null);
      })
      .catch(response => {
        console.error('Erro ao renovar token.', response);
        this.limparAccessToken();
        return Promise.resolve(null);
      });
  }

  limparAccessToken() {
    sessionStorage.removeItem('token');
    this.jwtPayload = null;
  }

  isAccessTokenInvalido() {
    const token = sessionStorage.getItem('token');

    return !token || this.jwtHelper.isTokenExpired(token);
  }

  temPermissao(permissao: string) {
    return this.jwtPayload && this.jwtPayload.authorities.includes(permissao);
  }

  getPermissao() {
    return this.jwtPayload != null ? this.jwtPayload.authorities : false;
  }

  temQualquerPermissao(roles) {
    for (const role of roles) {
      if (this.temPermissao(role)) {
        return true;
      }
    }

    return false;
  }

  private armazenarToken(token: string) {
    this.jwtPayload = this.jwtHelper.decodeToken(token);
    sessionStorage.setItem('token', token);
  }

  private carregarToken() {
    const token = sessionStorage.getItem('token');

    if (token) {
      this.armazenarToken(token);
    }
  }

  requestResetPassword(email: string) {
    const params = new HttpParams().set('email', email);
    return this.http.post(`${environment.api_url}auth/forgot_password`, {}, {params: params});
  }

  resetPassword(token: string, password: string) {
    const params = new HttpParams().append('token', token).append('password', password);
    return this.http.post(`${environment.api_url}auth/reset_password`, {}, {params: params});
  }

}
