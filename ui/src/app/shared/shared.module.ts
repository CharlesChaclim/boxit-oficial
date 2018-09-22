import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FooterComponent} from './footer/footer.component';
import {MaskDirective} from './mask/mask.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [
    FooterComponent,
    MaskDirective
  ],
  exports: [
    FooterComponent,
    MaskDirective
  ]
})

export class SharedModule {
}
