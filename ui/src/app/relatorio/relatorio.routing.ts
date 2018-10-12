import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {GerarComponent} from './gerar/gerar.component';

const routes: Routes = [
  {path: 'relatorio/new', component: GerarComponent},
];

export const RelatorioRoute: ModuleWithProviders = RouterModule.forChild(routes);
