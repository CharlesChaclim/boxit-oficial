import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from '@angular/router';
import {ProdutoRouting} from './produto.routing';
import { ListProdutoComponent } from './list-produto/list-produto.component';
import {FormsModule} from '@angular/forms';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    ProdutoRouting,
    FormsModule,
    NgbModule
  ],
  declarations: [ListProdutoComponent]
})
export class ProdutoModule { }
