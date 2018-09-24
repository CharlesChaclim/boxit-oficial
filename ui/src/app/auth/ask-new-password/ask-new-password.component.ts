import { Component, OnInit } from '@angular/core';
import {AuthService} from '../auth.service';
import {ToastrService} from 'ngx-toastr';
import {Router} from '@angular/router';
import {ErrorHandleService} from '../../core/error-handle.service';
import {NgForm} from '@angular/forms';

@Component({
  selector: 'app-ask-new-password',
  templateUrl: './ask-new-password.component.html',
  styleUrls: ['./ask-new-password.component.scss']
})
export class AskNewPasswordComponent implements OnInit {
  loading = false;

  constructor(
    private auth: AuthService,
    private toast: ToastrService,
    private router: Router,
    private errHandle: ErrorHandleService
  ) { }

  ngOnInit() {
  }

  requestReset(email: string, f: NgForm) {
    this.loading = true;
    this.auth.requestResetPassword(email)
      .subscribe(
        resp => {
          this.loading = false;
          this.toast.success('Verifique o email ' + email + ' para poder redefinir sua senha');
          this.router.navigate(['/login']);
        }, err => {
          f.reset();
          this.loading = false;
          this.errHandle.handle(err);
        }
      );
  }
}
