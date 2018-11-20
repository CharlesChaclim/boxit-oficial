import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {CadastrarComponent} from './cadastrar/cadastrar.component';
import {ListarComponent} from './listar/listar.component';
import {EditarComponent} from './editar/editar.component';
import {QuantidadeComponent} from './quantidade/quantidade.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'estoque/new', component: CadastrarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'estoque', component: ListarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'estoque/:id', component: EditarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'estoque/:id/quantidade', component: QuantidadeComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'estoque/:id/:edit', component: EditarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}}

];

export const EstoqueRoute: ModuleWithProviders = RouterModule.forChild(routes);
