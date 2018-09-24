import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {Cliente} from '../../core/model';
import {CorreiosService} from '../../shared/correios.service';
import {ClienteService} from '../cliente.service';
import {Router} from '@angular/router';

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
  csenha = '';
  constructor(
    private correiosService: CorreiosService,
    private servico: ClienteService,
    private route: Router
  ) { }

  ngOnInit() {
  }

  buscaEndereco() {
    this.correiosService.getEndereco(this.c.cep).subscribe(
      r => {
        this.c.cidade = r.cidade;
        this.c.logadouro = r.endereco;
        this.c.bairro = r.bairro;
        this.c.estado = r.uf;
      }
    );
  }

  confirmasenha(): boolean {
    return this.c.password !== this.csenha;
  }

  editar() {
    print();
  }

}

