import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListComponent} from './list/list.component';
import {ViewComponent} from './view/view.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'pedido', component: ListComponent, canActivate: [AuthGuard], data: {roles: ['CLIENTE']}},
  {path: 'pedido/:id', component: ViewComponent, canActivate: [AuthGuard], data: {roles: ['CLIENTE']}}
];

export const PedidoRoute: ModuleWithProviders = RouterModule.forChild(routes);
