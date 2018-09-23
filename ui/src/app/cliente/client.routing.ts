import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListComponent} from './list/list.component';
import {EditComponent} from './edit/edit.component';
import {CreateComponent} from './create/create.component';

const routes: Routes = [
  {path: 'cliente', component: ListComponent},
  {path: 'cliente/new', component: CreateComponent},
  {path: 'cliente/:id', component: EditComponent}
];

export const ClienteRoute: ModuleWithProviders = RouterModule.forChild(routes);
