import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {CoreModule} from './core/core.module';
import {RouterModule} from '@angular/router';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';

import {PagamentoComponent} from './pagamento/pagamento.component';
import {SharedModule} from './shared/shared.module';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {CurrencyMaskModule} from 'ng2-currency-mask';

@NgModule({
  declarations: [
    AppComponent,
    PagamentoComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
        progressBar: true,
        preventDuplicates: true,
        progressAnimation: 'increasing',
        resetTimeoutOnDuplicate: true,
        timeOut: 10000,
        positionClass: 'toast-bottom-left'
      }),
    CoreModule,
    RouterModule,
    SharedModule,
    NgbModule,
    CommonModule,
    FormsModule,
    CurrencyMaskModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
