import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import {CoreModule} from './core/core.module';
import {CreateComponent} from "./usuario/cliente/create/create.component";
import {FormsModule} from "@angular/forms";
import {SharedModule} from "./shared/shared.module";
import {EditComponent} from "./usuario/cliente/edit/edit.component";

@NgModule({
  declarations: [
    AppComponent,
    CreateComponent,
    EditComponent
  ],
  imports: [
    BrowserModule,
    CoreModule,
    FormsModule,
    SharedModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
