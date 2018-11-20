import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {NotFoundComponent} from './core/not-found/not-found.component';
import {UnauthorizedComponent} from './core/unauthorized/unauthorized.component';
import {PagamentoComponent} from './pagamento/pagamento.component';
import {AuthGuard} from './auth/auth.guard';

export const routes: Routes = [
  {path: '', redirectTo: 'login', pathMatch: 'full'},
  {path: 'pagamento', component: PagamentoComponent, canActivate: [AuthGuard], data: {roles: ['FUNCIONARIO', 'GERENTE']}},
  {path: '404', component: NotFoundComponent},
  {path: '401', component: UnauthorizedComponent},
  {path: '**', redirectTo: '404'}
];

export const AppRouting: ModuleWithProviders = RouterModule.forRoot(routes);
