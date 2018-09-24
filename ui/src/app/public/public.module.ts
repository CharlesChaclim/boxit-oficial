import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home/home.component';
import {RouterModule} from '@angular/router';
import {PublicRouting} from './public.routing';

@NgModule({
  imports: [
    CommonModule,
    RouterModule,
    PublicRouting
  ],
  declarations: [HomeComponent]
})
export class PublicModule { }
