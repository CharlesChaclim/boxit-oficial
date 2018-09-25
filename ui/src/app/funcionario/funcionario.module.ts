import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListFuncionarioComponent} from './list-funcionario/list-funcionario.component';
import {ViewFuncionarioComponent} from './view-funcionario/view-funcionario.component';
import {RouterModule} from '@angular/router';
import {FuncionarioRoute} from './funcionario.routing';
import {FormsModule} from '@angular/forms';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FuncionarioRoute,
    FormsModule
  ],
  declarations: [
    ListFuncionarioComponent,
    ViewFuncionarioComponent
  ]
})
export class FuncionarioModule { }
