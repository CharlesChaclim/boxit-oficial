import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GerarComponent} from './gerar/gerar.component';
import {RelatorioRoute} from './relatorio.routing';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    RelatorioRoute,
    FormsModule,
    SharedModule,
    NgbModule,
    SweetAlert2Module
  ],
  declarations: [
    GerarComponent
  ]
})
export class RelatorioModule { }
