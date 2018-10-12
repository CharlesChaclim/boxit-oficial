import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {GerarComponent} from './gerar/gerar.component';
import {RelatorioRoute} from './relatorio.routing';

@NgModule({
  imports: [
    CommonModule,
    RelatorioRoute
  ],
  declarations: [
    GerarComponent
  ]
})
export class RelatorioModule { }
