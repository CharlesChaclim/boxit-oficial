import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewCartComponent } from './view-cart/view-cart.component';
import {RouterModule} from '@angular/router';
import {CarrinhoRoute} from './carrinho.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    CarrinhoRoute
  ],
  declarations: [ViewCartComponent]
})
export class CarrinhoModule { }
