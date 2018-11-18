import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ListComponent} from './list/list.component';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ViewComponent} from './view/view.component';
import {PedidoRoute} from './pedido.routing';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    RouterModule,
    PedidoRoute,
    NgbModule
  ],
  declarations: [
    ViewComponent,
    ListComponent
  ]
})
export class PedidoModule { }
