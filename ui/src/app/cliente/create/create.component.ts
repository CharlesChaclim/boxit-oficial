import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {Cliente} from '../../core/model';
import {CorreiosService} from '../../shared/correios.service';

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
  constructor(
    private correiosService: CorreiosService
  ) { }

  ngOnInit() {
  }

  teste() {
    console.log('t');
  }

  buscaEndereco() {
    this.correiosService.getEndereco(this.c.cep).subscribe(
      r => {
        this.c.cidade = r.cidade;
        this.c.endereco = r.endereco;
        this.c.bairro = r.bairro;
        this.c.estado = r.uf;
      }
    );
  }

  confirmasenha(): boolean {
    return this.c.senha !== this.c.csenha;
  }

  cadastrar() {
    print();
  }

}
