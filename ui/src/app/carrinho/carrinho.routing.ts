import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {AuthGuard} from '../auth/auth.guard';
import {ViewCartComponent} from './view-cart/view-cart.component';

const routes: Routes = [
  {path: 'carrinho/:id', component: ViewCartComponent, canActivate: [AuthGuard], data: {roles: ['CLIENTE']}}
];

export const CarrinhoRoute: ModuleWithProviders = RouterModule.forChild(routes);
