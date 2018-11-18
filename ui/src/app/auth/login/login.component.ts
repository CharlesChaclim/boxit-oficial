import { Component, OnInit } from '@angular/core';
import {Login} from '../../core/model';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {ErrorHandleService} from '../../core/error-handle.service';
import {NgForm} from '@angular/forms';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import swal from 'sweetalert2';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  login = new Login();
  public personMask = MyMaskUtil.PERSON_MASK_GENERATOR;

  constructor(
    private auth: AuthService,
    private router: Router,
    private errHandle: ErrorHandleService
  ) { }

  ngOnInit() {}

  logar(form: NgForm) {
    this.auth.login(this.login.username, this.login.password)
      .then(() => {
        this.router.navigate(['/cliente']);
      }).catch(err => {
      form.reset({username: this.login.username});
      this.errHandle.handle(err);
    });
  }

  help() {
    swal({
        title: 'Ajuda',
        html: 'O campo username deve ser preenchido com o CNPJ informado no cadastro, mas caso seja um funcionário da nossa empressa' +
          ' preencha o campo com o seu CPF.',
        type: 'info'
      }
    );
  }
}
