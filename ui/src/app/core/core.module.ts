import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EstoqueModule} from '../estoque/estoque.module';
import {ClienteModule} from '../cliente/cliente.module';
import {NotFoundComponent} from './not-found/not-found.component';
import {UnauthorizedComponent} from './unauthorized/unauthorized.component';
import {AppRouting} from '../app.routing';
import { TopbarComponent } from './topbar/topbar.component';
import { FooterComponent } from './footer/footer.component';

@NgModule({
  imports: [
    CommonModule,
    EstoqueModule,
    ClienteModule,
    AppRouting
  ],
  declarations: [
    NotFoundComponent,
    UnauthorizedComponent,
    TopbarComponent,
    FooterComponent
  ],
  exports: [
    TopbarComponent,
    FooterComponent
  ]
})
export class CoreModule { }
