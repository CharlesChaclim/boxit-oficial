import { Component, OnInit } from '@angular/core';
import {Login} from '../../core/model';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {ErrorHandleService} from '../../core/error-handle.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  login = new Login();

  constructor(
    private auth: AuthService,
    private router: Router,
    private errHandle: ErrorHandleService
  ) { }

  ngOnInit() {}

  logar(form: NgForm) {
    this.auth.login(this.login.username, this.login.password)
      .then(() => {
        if (this.auth.getPermissao()[0] === 'PETIANO') {
          this.router.navigate(['/dashboard']);
        } else if (this.auth.getPermissao()[0] === 'HELPER') {
          this.router.navigate(['/helper/evento']);
        } else {
          this.router.navigate(['/']);
        }
      }).catch(err => {
      form.reset({username: this.login.username});
      this.errHandle.handle(err);
    });
  }

}
