import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListarComponent} from './listar/listar.component';
import {CadastrarComponent} from './cadastrar/cadastrar.component';
import {EditarComponent} from './editar/editar.component';
import {RouterModule} from '@angular/router';
import {EstoqueRoute} from './estoque.routing';
import {FormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SharedModule} from '../shared/shared.module';
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';
import {CurrencyMaskModule} from 'ng2-currency-mask';
import { QuantidadeComponent } from './quantidade/quantidade.component';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    EstoqueRoute,
    FormsModule,
    SharedModule,
    NgbModule,
    SweetAlert2Module,
    CurrencyMaskModule
  ],
  declarations: [
    ListarComponent,
    CadastrarComponent,
    EditarComponent,
    QuantidadeComponent
  ]
})
export class EstoqueModule { }
