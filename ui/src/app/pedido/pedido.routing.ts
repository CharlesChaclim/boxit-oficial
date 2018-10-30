import {RouterModule, Routes} from '@angular/router';
import {ModuleWithProviders} from '@angular/core';
import {ListComponent} from './list/list.component';
import {ViewComponent} from './view/view.component';

const routes: Routes = [
  {path: 'pedido', component: ListComponent},
  {path: 'pedido/:id', component: ViewComponent}
];

export const PedidoRoute: ModuleWithProviders = RouterModule.forChild(routes);
