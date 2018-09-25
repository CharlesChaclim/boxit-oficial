import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {CriarComponent} from './criar/criar.component';
import {ListarComponent} from './listar/listar.component';
import {EditarComponent} from './editar/editar.component';
import {RouterModule} from '@angular/router';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';
import {CategoriaRoute} from './categoria.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    CategoriaRoute,
    FormsModule,
    SharedModule,
    NgbModule,
    SweetAlert2Module
  ],
  declarations: [
    CriarComponent,
    ListarComponent,
    EditarComponent
  ]
})
export class CategoriaModule { }
