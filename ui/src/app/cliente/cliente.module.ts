import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EditComponent} from './edit/edit.component';
import {ListComponent} from './list/list.component';
import {FormsModule} from '@angular/forms';
import {SharedModule} from '../shared/shared.module';
import {RouterModule} from '@angular/router';
import {ClienteRoute} from './client.routing';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    RouterModule,
    ClienteRoute
  ],
  declarations: [
    EditComponent,
    ListComponent
  ]
})
export class ClienteModule { }
