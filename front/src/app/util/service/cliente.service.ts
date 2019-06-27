import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Cliente } from '../domains/cliente.domain';

@Injectable()
export class ClienteService {
    constructor(private http: HttpClient) { }
    baseUrl: string = 'http://localhost:8080/clientes';

    save(cliente: Cliente) {
        return this.http.post(this.baseUrl, cliente);
    }

    getAll(): Observable<Cliente[]> {
        return this.http.get<Cliente[]>(this.baseUrl);
    }

    delete(id): Observable<any> {
        return this.http.delete(this.baseUrl + '/' + id);
    }

    getOne(id: any): Observable<Cliente> {
        return this.http.get<Cliente>(this.baseUrl + '/' + id);
    }
}