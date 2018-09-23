import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListFuncionarioComponent} from './list-funcionario/list-funcionario.component';
import {ViewFuncionarioComponent} from './view-funcionario/view-funcionario.component';

const routes: Routes = [
  {path: 'funcionario', component: ListFuncionarioComponent},
  {path: 'funcionario/new', component: ViewFuncionarioComponent},
  {path: 'funcionario/:id', component: ViewFuncionarioComponent},
  {path: 'funcionario/:id/edit', component: ViewFuncionarioComponent}
];

export const FuncionarioRoute: ModuleWithProviders = RouterModule.forChild(routes);
