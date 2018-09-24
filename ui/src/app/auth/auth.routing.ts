import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {ModuleWithProviders} from '@angular/core';
import {ConfirmComponent} from './confirm/confirm.component';
import {RegisterComponent} from './register/register.component';
import {AskNewPasswordComponent} from './ask-new-password/ask-new-password.component';
import {ChangePasswordComponent} from './change-password/change-password.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent },
  {path: 'confirmation', component: ConfirmComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'forget_password', component: AskNewPasswordComponent},
  {path: 'reset_password', component: ChangePasswordComponent}
];

export const AuthRoute: ModuleWithProviders = RouterModule.forChild(routes);
