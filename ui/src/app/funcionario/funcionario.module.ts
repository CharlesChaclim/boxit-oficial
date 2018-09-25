import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListFuncionarioComponent} from './list-funcionario/list-funcionario.component';
import {ViewFuncionarioComponent} from './view-funcionario/view-funcionario.component';
import {RouterModule} from '@angular/router';
import {FuncionarioRoute} from './funcionario.routing';
import {FormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    FuncionarioRoute,
    FormsModule,
    SharedModule,
    NgbModule,
    SweetAlert2Module
  ],
  declarations: [
    ListFuncionarioComponent,
    ViewFuncionarioComponent
  ]
})
export class FuncionarioModule { }
