import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {CriarComponent} from './criar/criar.component';
import {ListarComponent} from './listar/listar.component';
import {EditarComponent} from './editar/editar.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'categoria/new', component: CriarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'categoria', component: ListarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'categoria/:id', component: EditarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: 'categoria/:id/:edit', component: EditarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}}
];

export const CategoriaRoute: ModuleWithProviders = RouterModule.forChild(routes);
