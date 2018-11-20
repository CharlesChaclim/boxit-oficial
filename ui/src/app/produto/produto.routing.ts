import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListProdutoComponent} from './list-produto/list-produto.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'categoria-produto/:cat/:name', component: ListProdutoComponent, canActivate: [AuthGuard], data: {roles: ['CLIENTE']}}
];

export const ProdutoRouting: ModuleWithProviders = RouterModule.forRoot(routes);
