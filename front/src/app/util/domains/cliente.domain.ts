
export class Cliente {
    id: number;
    nome: string;
    email: string;
    cpfCnpj: string;
    tipo: number;
    logradouro: string;
    numero: string;
    complemento: string;
    bairro: string;
    cep: string;
    telefone1: string;
    telefone2: string;
    telefone3: string;
    cidadeId: number;
    senha: string;

    constructor(id?: number, nome?: string, email?: string, cpfCnpj?: string, tipo?: number, logradouro?: string, numero?: string, complemento?: string, bairro?: string, cep?: string, telefone1?: string, telefone2?: string, telefone3?: string, cidadeId?: number, senha?: string) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.cpfCnpj = cpfCnpj;
        this.tipo = tipo;
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cep = cep;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
        this.telefone3 = telefone3;
        this.cidadeId = cidadeId;
        this.senha = senha;
    }
}