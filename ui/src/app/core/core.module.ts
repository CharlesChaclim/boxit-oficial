import {LOCALE_ID, NgModule} from '@angular/core';
import localePt from '@angular/common/locales/pt';
import {CommonModule, registerLocaleData} from '@angular/common';
import {EstoqueModule} from '../estoque/estoque.module';
import {ClienteModule} from '../cliente/cliente.module';
import {NotFoundComponent} from './not-found/not-found.component';
import {UnauthorizedComponent} from './unauthorized/unauthorized.component';
import {AppRouting} from '../app.routing';
import {TopbarComponent} from './topbar/topbar.component';
import {FooterComponent} from './footer/footer.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';
import {CurrencyMaskModule} from 'ng2-currency-mask';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {HttpClientModule} from '@angular/common/http';
import {FuncionarioModule} from '../funcionario/funcionario.module';
import {AuthModule} from '../auth/auth.module';
import {CategoriaModule} from '../categoria/categoria.module';

registerLocaleData(localePt, 'pt-BR');

@NgModule({
  imports: [
    CommonModule,
    EstoqueModule,
    CategoriaModule,
    ClienteModule,
    AppRouting,
    NgbModule.forRoot(),
    SweetAlert2Module.forRoot({
      cancelButtonText: 'Voltar',
      confirmButtonText: 'Confirmar',
      showCancelButton: true
    }),
    CurrencyMaskModule,
    HttpClientModule,
    FuncionarioModule,
    AuthModule
  ],
  declarations: [
    NotFoundComponent,
    UnauthorizedComponent,
    TopbarComponent,
    FooterComponent
  ],
  exports: [
    TopbarComponent,
    FooterComponent
  ],
  providers: [
    { provide: LOCALE_ID, useValue: 'pt-BR' }
  ]
})
export class CoreModule { }
