import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListComponent} from './list/list.component';
import {EditComponent} from './edit/edit.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'cliente', component: ListComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'cliente/:id', component: EditComponent, canActivate: [AuthGuard], data: {roles: ['CLIENTE', 'FUNCIONARIO', 'GERENTE']}},
  {path: 'cliente/:id/:edit', component: EditComponent, canActivate: [AuthGuard], data: {roles: ['CLIENTE', 'FUNCIONARIO', 'GERENTE']}}
];

export const ClienteRoute: ModuleWithProviders = RouterModule.forChild(routes);
