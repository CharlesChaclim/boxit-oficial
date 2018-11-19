import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {CadastrarComponent} from './cadastrar/cadastrar.component';
import {ListarComponent} from './listar/listar.component';
import {EditarComponent} from './editar/editar.component';
import {QuantidadeComponent} from './quantidade/quantidade.component';

const routes: Routes = [
  {path: 'estoque/new', component: CadastrarComponent},
  {path: 'estoque', component: ListarComponent},
  {path: 'estoque/:id', component: EditarComponent},
  {path: 'estoque/:id/quantidade', component: QuantidadeComponent},
  {path: 'estoque/:id/:edit', component: EditarComponent}

];

export const EstoqueRoute: ModuleWithProviders = RouterModule.forChild(routes);
