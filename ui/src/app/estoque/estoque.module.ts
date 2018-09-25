import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ListarComponent } from './listar/listar.component';
import { CadastrarComponent } from './cadastrar/cadastrar.component';
import { EditarComponent } from './editar/editar.component';
import {EstoqueRoute} from './estoque.routing';

@NgModule({
  imports: [
    CommonModule,
    EstoqueRoute
  ],
  declarations: [
    ListarComponent,
    CadastrarComponent,
    EditarComponent
  ]
})
export class EstoqueModule { }
