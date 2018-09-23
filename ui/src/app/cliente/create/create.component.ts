import {Component, OnInit} from '@angular/core';
import {MyMaskUtil} from '../../shared/mask/my-mask.util';
import {Cliente} from '../../core/model';
import {CorreiosService} from '../../shared/correios.service';
import {Router} from '@angular/router';
import {ClienteService} from '../cliente.service';
import swal from 'sweetalert2';

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
    return this.c.senha !== this.c.csenha;
  }

  confimacnpj(): boolean {
    this.servico.cnpjExist(this.c).subscribe(() => {
        swal(
          'Erro!',
          'CNPJ já cadastrado',
          'error'
        );
        return true;
      }
    );
    return false;
  }

  confimaemail(): boolean {
    this.servico.emailExist(this.c).subscribe(() => {
        swal(
          'Erro!',
          'Email já cadastrado',
          'error'
        );
        return true;
      }
    );
    return false;
  }

  cadastrar() {
    this.servico.create(this.c).subscribe(
      () => {
        this.route.navigate(['/cliente']);
      }, (e) => {
        swal(
          'Erro!',
          'Erro na validação com o servidor \n ' + e,
          'error'
        );
      }
    );
  }

}
