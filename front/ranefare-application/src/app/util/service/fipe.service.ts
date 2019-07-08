import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Brand } from '../domains/brand.domain';
import { ModelResponse } from '../domains/responses/model.response';
import { Detail } from '../domains/details.domain';

@Injectable()
export class FipeService {

  constructor(private http: HttpClient) { }
  url = '';

  getBrands(): Observable<Brand[]> {
    return this.http.get<Brand[]>(this.url);
  }

  getModels(brandId: number): Observable<ModelResponse> {
    return this.http.get<ModelResponse>(this.url + '/' + brandId)
  }

  getDetails(brandId: number, modelId: String): Observable<Detail> {
    return this.http.get<Detail>(this.url);
  }
}
