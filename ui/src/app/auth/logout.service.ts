import { Injectable } from '@angular/core';
import {MoneyHttp} from './money.http';
import {AuthService} from './auth.service';
import {environment} from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class LogoutService {
  tokensRenokeUrl: string;

  constructor(
    private http: MoneyHttp,
    private auth: AuthService
  ) {
    this.tokensRenokeUrl = `${environment.api_url}tokens/revoke`;
  }

  logout() {
    return this.http.delete(this.tokensRenokeUrl, { withCredentials: true })
      .toPromise()
      .then(() => {
        this.auth.limparAccessToken();
      }).catch(() => {
        this.auth.limparAccessToken();
      });
  }
}
