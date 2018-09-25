import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListComponent} from './list/list.component';
import {EditComponent} from './edit/edit.component';

const routes: Routes = [
  {path: 'cliente', component: ListComponent},
  {path: 'cliente/:id', component: EditComponent},
  {path: 'cliente/:id/:edit', component: EditComponent}
];

export const ClienteRoute: ModuleWithProviders = RouterModule.forChild(routes);
