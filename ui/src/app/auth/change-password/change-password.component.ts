import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, NgForm, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthService} from '../auth.service';
import {ToastrService} from 'ngx-toastr';
import {ErrorHandleService} from '../../core/error-handle.service';

export class PasswordValidation {

  static MatchPassword(AC: AbstractControl) {
    const password = AC.get('password').value;
    const confirmPassword = AC.get('confirmPassword').value;
    if (password !== confirmPassword) {
      AC.get('confirmPassword').setErrors( {MatchPassword: true} );
    } else {
      return null;
    }
  }
}

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html'
})
export class ChangePasswordComponent implements OnInit {
  loading = false;
  token: string;
  form: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private auth: AuthService,
    private toast: ToastrService,
    private router: Router,
    private errHandle: ErrorHandleService,
    private fb: FormBuilder
  ) {
    this.form = fb.group({
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, {
      validator: PasswordValidation.MatchPassword
    });
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe(
      params => {
        this.token = params.get('token');
      }
    );
  }

  resetPassword(f: NgForm) {
    this.loading = true;
    this.auth.resetPassword(this.token, this.form.value.password)
      .subscribe(
        resp => {
          this.loading = false;
          this.router.navigate(['/login']);
          this.toast.success(resp['success']);
        }, err => {
          this.loading = false;
          this.errHandle.handle(err);
          f.reset({token: this.token});
        }
      );
  }
}
