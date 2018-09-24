import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {LoginComponent} from './login/login.component';
import {ConfirmComponent} from './confirm/confirm.component';
import {AskNewPasswordComponent} from './ask-new-password/ask-new-password.component';
import {ChangePasswordComponent} from './change-password/change-password.component';
import {environment} from '../../environments/environment';
import {JwtModule} from '@auth0/angular-jwt';
import {RegisterComponent} from './register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {AuthRoute} from './auth.routing';
import {SharedModule} from '../shared/shared.module';

export function tokenGetter() {
  return sessionStorage.getItem('token');
}

@NgModule({
  imports: [
    CommonModule,
    JwtModule.forRoot({
      config: {
        tokenGetter: tokenGetter,
        whitelistedDomains: environment.tokenWhitelistedDomains,
        blacklistedRoutes: environment.tokenBlacklistedRoutes
      }
    }),
    FormsModule,
    ReactiveFormsModule,
    AuthRoute,
    SharedModule
  ],
  declarations: [
    LoginComponent,
    ConfirmComponent,
    AskNewPasswordComponent,
    ChangePasswordComponent,
    RegisterComponent
  ]
})
export class AuthModule { }
