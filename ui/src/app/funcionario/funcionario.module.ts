import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListFuncionarioComponent} from './list-funcionario/list-funcionario.component';
import {ViewFuncionarioComponent} from './view-funcionario/view-funcionario.component';
import {ChangePasswordFuncionarioComponent} from './change-password-funcionario/change-password-funcionario.component';
import {RouterModule} from '@angular/router';
import {FuncionarioRoute} from './funcionario.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FuncionarioRoute
  ],
  declarations: [
    ListFuncionarioComponent,
    ViewFuncionarioComponent,
    ChangePasswordFuncionarioComponent
  ]
})
export class FuncionarioModule { }
