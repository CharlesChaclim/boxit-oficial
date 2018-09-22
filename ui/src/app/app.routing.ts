import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {NotFoundComponent} from './core/not-found/not-found.component';
import {UnauthorizedComponent} from './core/unauthorized/unauthorized.component';

export const routes: Routes = [
  {path: '404', component: NotFoundComponent},
  {path: '401', component: UnauthorizedComponent},
  {path: '**', redirectTo: '404'}
];

export const AppRouting: ModuleWithProviders = RouterModule.forRoot(routes);
