import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Brand } from '../domains/brand.domain';
import { ModelResponse } from '../domains/responses/model.response';
import { Detail } from '../domains/details.domain';
import { environment } from '../../../environments/environment';

@Injectable()
export class FipeService {

  constructor(private http: HttpClient) { }
  url = environment.baseUrlServiceFipe;

  getBrands(): Observable<any> {
    return this.http.get<any>(this.url + '/brands');
  }

  getModels(brandId: number): Observable<ModelResponse> {
    return this.http.get<ModelResponse>(this.url + '/brands/' + brandId + '/models')
  }

  getDetails(brandId: number, modelId: String): Observable<Detail> {
    return this.http.get<Detail>(this.url + '/brands/' + brandId + '/models/' + modelId + '/details');
  }
}
