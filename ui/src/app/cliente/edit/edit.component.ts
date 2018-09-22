import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {Cliente} from '../../core/model';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.scss']
})
export class EditComponent implements OnInit {
  public cpfMask = MyMaskUtil.CPF_MASK_GENERATOR;
  public cnpjMask = MyMaskUtil.CNPJ_MASK_GENERATOR;
  public cepMask = MyMaskUtil.CEP_MASK_GENERATOR;
  public phoneMask = MyMaskUtil.DYNAMIC_PHONE_MASK_GENERATOR;
  c = new Cliente();
  constructor() { }

  ngOnInit() {
  }

  confirmasenha(): boolean {
    return this.c.senha !== this.c.csenha;
  }

  editar(){
    print();
  }

}

