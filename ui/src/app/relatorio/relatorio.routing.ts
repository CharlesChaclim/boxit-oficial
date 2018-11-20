import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {GerarComponent} from './gerar/gerar.component';
import {AuthGuard} from '../auth/auth.guard';

const routes: Routes = [
  {path: 'relatorio/new', component: GerarComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
];

export const RelatorioRoute: ModuleWithProviders = RouterModule.forChild(routes);
