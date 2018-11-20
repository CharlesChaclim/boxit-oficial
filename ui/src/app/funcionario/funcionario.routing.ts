import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListFuncionarioComponent} from './list-funcionario/list-funcionario.component';
import {ViewFuncionarioComponent} from './view-funcionario/view-funcionario.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'funcionario', component: ListFuncionarioComponent, canActivate: [AuthGuard], data: {roles: ['GERENTE']}},
  {path: 'funcionario/new', component: ViewFuncionarioComponent, canActivate: [AuthGuard], data: {roles: ['GERENTE']}},
  {path: 'funcionario/:id', component: ViewFuncionarioComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'funcionario/:id/:edit', component: ViewFuncionarioComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}}
];

export const FuncionarioRoute: ModuleWithProviders = RouterModule.forChild(routes);
