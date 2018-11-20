export class Cliente {
  bairro: string;
  cep:	string;
  cidade:	string;
  complemento:	string;
  cpf:	string;
  email:	string;
  endereco:	string;
  estado:	string;
  cnpj: string;
  nome:	string;
  nomeFantasia:	string;
  numero: string;
  password:	string;
  telefone:	string;
}

export class ClienteEdit {
  cpf:	string;
  email:	string;
  cnpj: string;
  nome:	string;
  nomeFantasia:	string;
  password:	string;
  telefone:	string;
  endereco = new Endereco();
}

export class Endereco {
  bairro: string;
  cidade: string;
  estado: string;
  complemento: string;
  endereco: string;
  numero: string;
  cep:	string;
}

export class EnderecoCorreios {
  endereco: string;
  bairro: string;
  cidade: string;
  uf: string;
}

export class Login {
  username: string;
  password: string;
}

export class Funcionario {
  cargo: string;
  cpf: string;
  email: string;
  nome: string;
  telefone: string;
  username: string;
  password: string;
}

export class Page {
  content: any;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: any;
  size: number;
  sort: any;
  totalElements: number;
  totalPages: number;
}

export class Estoque {
  nome: string;
  preco: number;
  desconto: number;
  descricao: string;
  unidadeLote: number;
  qtd: number;
  sku: string;
  fotos: Array<string>;
  categoriaId: number;
}

export class EstoqueEdit {
  nome: string;
  preco: number;
  desconto: number;
  descricao: string;
  unidadeLote: number;
  qtd: number;
  sku: string;
  fotos: Array<string>;
  categoriaId: number;
}

export class Categoria {
  nome: string;
}

export class CategoriaEdit {
  nome: string;
}

export class Relatorio {
  dataInicio: Date;
  dataFim: Date;
  tipo: number;
  cnpj: string;
  sku: string;
}

export class Pagamento {
  dataPagamento: Date;
  nrBoleto: string;
}

export class AlterarQuantidade {
  motivo: number;
  motivoOutro: string;
  qtd: number;
  precoCompra: number;
}

export class Carrinho {
  id: string;
  produtoId: string;
  qty: string;
}

export class Cart {
  id: string;
  items: any;
}
