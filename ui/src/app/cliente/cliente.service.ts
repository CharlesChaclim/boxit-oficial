import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {Cliente} from '../core/model';

@Injectable()
export class ClienteService {
  private service = `http://localhost:8080/swagger-ui.html#/cliente/`;

  constructor(private http: HttpClient, private router: Router) { }

  getAll() {
    return this.http.get(this.service);
  }

  excluir(id: string) {
    return this.http.delete(this.service + id);
  }

  getOne(id: string) {
    return this.http.get<Cliente>(this.service + id);
  }

  emailExist(cliente: Cliente) {
    return this.http.post('/email' , cliente.email);
  }

  cnpjExist(cliente: Cliente) {
    return this.http.post('/cnpj' , cliente.cnpj);
  }

  create(cliente: Cliente) {
    const bairro = cliente.bairro;
    const cep = cliente.cep;
    const cidade = cliente.cidade;
    const cnpj = cliente.cnpj;
    const complemento = cliente.complemento;
    const cpf = cliente.cpf;
    const email = cliente.email;
    const logadouro = cliente.logadouro;
    const estado = cliente.estado;
    const nome = cliente.nome;
    const nomeFantasia = cliente.nomef;
    const numero = cliente.numero;
    const senha = cliente.senha;
    const telefone = cliente.telefone;
    return this.http.post(this.service,
      {bairro, cep, cidade, cnpj, complemento, cpf, email, logadouro, estado, nome, nomeFantasia, numero, senha, telefone});
  }

  update(id: string, cliente: Cliente) {
    const bairro = cliente.bairro;
    const cep = cliente.cep;
    const cidade = cliente.cidade;
    const cnpj = cliente.cnpj;
    const complemento = cliente.complemento;
    const cpf = cliente.cpf;
    const email = cliente.email;
    const logadouro = cliente.logadouro;
    const estado = cliente.estado;
    const nome = cliente.nome;
    const nomeFantasia = cliente.nomef;
    const numero = cliente.numero;
    const senha = cliente.senha;
    const telefone = cliente.telefone;
    return this.http.put(this.service + id,
      {bairro, cep, cidade, cnpj, complemento, cpf, email, logadouro, estado, nome, nomeFantasia, numero, senha, telefone});
  }

}
