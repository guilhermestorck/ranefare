import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from 'rxjs';
import { Cidade } from '../domains/cidade.domain';

@Injectable()
export class CidadeService {
    constructor(private http: HttpClient) { }
    baseUrl: string = 'http://localhost:8080/cidades';
    url = 'https://servicodados.ibge.gov.br/api/v1/localidades/estados/'

    getAll() {
        return this.http.get<Cidade[]>(this.baseUrl);
    }

    getCidades(idEstado: number): Observable<Cidade[]> {
      return this.http.get<Cidade[]>(this.url + idEstado + '/municipios');
    }
}