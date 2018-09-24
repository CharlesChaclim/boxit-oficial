export class Cliente {
  bairro: string;
  cep:	string;
  cidade:	string;
  complemento:	string;
  cpf:	string;
  email:	string;
  logadouro:	string;
  estado:	string;
  cnpj: string;
  nome:	string;
  nomeFantasia:	string;
  numero: string;
  password:	string;
  telefone:	string;
  endereco: Endereco;
}

export class Endereco {
  logadouro: string;
  bairro: string;
  cidade: string;
  estado: string;
  cep: string;
  numero: string;
  complemento: string;
  endereco: string;
  uf: string;
}

export class Login {
  username: string;
  password: string;
}
