import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {CriarComponent} from './criar/criar.component';
import {ListarComponent} from './listar/listar.component';
import {EditarComponent} from './editar/editar.component';

const routes: Routes = [
  {path: 'categoria/new', component: CriarComponent},
  {path: 'categoria', component: ListarComponent},
  {path: 'categoria/:id', component: EditarComponent},
  {path: 'categoria/:id/:edit', component: EditarComponent}
];

export const CategoriaRoute: ModuleWithProviders = RouterModule.forChild(routes);
