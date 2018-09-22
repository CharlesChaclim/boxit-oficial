import { Component, OnInit } from '@angular/core';
import {Cliente} from "../cliente.model";
import {MyMaskUtil} from "../../../shared/mask/my-mask.util";
import {printLine} from "tslint/lib/verify/lines";

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {
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

  cadastrar(){
    print();
  }

}
