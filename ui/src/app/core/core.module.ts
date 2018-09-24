import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {EstoqueModule} from '../estoque/estoque.module';
import {ClienteModule} from '../cliente/cliente.module';
import {NotFoundComponent} from './not-found/not-found.component';
import {UnauthorizedComponent} from './unauthorized/unauthorized.component';
import {AppRouting} from '../app.routing';
import { TopbarComponent } from './topbar/topbar.component';
import { FooterComponent } from './footer/footer.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {SweetAlert2Module} from '@toverux/ngx-sweetalert2';
import {CurrencyMaskModule} from 'ng2-currency-mask';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import {HttpClientModule} from '@angular/common/http';
import {FuncionarioModule} from '../funcionario/funcionario.module';
import {PublicModule} from '../public/public.module';

@NgModule({
  imports: [
    CommonModule,
    EstoqueModule,
    ClienteModule,
    AppRouting,
    NgbModule.forRoot(),
    SweetAlert2Module.forRoot({
      buttonsStyling: false,
      confirmButtonClass: 'btn btn-primary',
      cancelButtonClass: 'btn btn-secondary'
    }),
    CurrencyMaskModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      progressBar: true,
      preventDuplicates: true,
      progressAnimation: 'increasing',
      resetTimeoutOnDuplicate: true,
      timeOut: 10000,
      positionClass: 'toast-bottom-left'
    }),
    HttpClientModule,
    FuncionarioModule,
    PublicModule
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
  ]
})
export class CoreModule { }
