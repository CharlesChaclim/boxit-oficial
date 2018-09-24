import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import {Router} from '@angular/router';
import {NotAuthenticatedError} from '../auth/money.http';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ErrorHandleService {

  constructor(
    private router: Router,
    private toastr: ToastrService
  ) { }

  handle(errResp: any) {
    let msg: string;

    if (typeof errResp === 'string') {
      msg = errResp;
    } else if (errResp instanceof  NotAuthenticatedError) {
      msg = 'Sua sessão expirou';
      this.router.navigate(['/login']);
    } else if (errResp instanceof HttpErrorResponse
      && errResp.status >= 400 && errResp.status <= 499
    ) {
      msg = 'Ocorreu um erro ao processar a sua solicitação';

      if (errResp.status === 400) {
          msg = errResp.error.message;
      }

      if (errResp.status === 404 && errResp.message != null) {
        msg = errResp.error.message;
      }

      try {
        if (msg === null) {
          msg = errResp.error.error;
        }
      } catch (e) {}
    } else if (errResp instanceof HttpErrorResponse
      && errResp.status >= 500
    ) {
        msg = 'Erro ao processar serviço remoto. Tente novamente.';
        console.error('Ocorreu um erro', errResp);
    }
    this.toastr.error(msg);
  }
}
