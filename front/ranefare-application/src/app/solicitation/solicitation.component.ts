import { Component, OnInit } from '@angular/core';
import { FipeService } from '../util/service/fipe.service';
import { PlanRequest } from '../util/domains/requests/plans.requests';
import { Brand } from '../util/domains/brand.domain';
import { ModelResponse } from '../util/domains/responses/model.response';
import { Detail } from '../util/domains/details.domain';

@Component({
  selector: 'solicitation',
  templateUrl: './solicitation.component.html',
  styleUrls: ['./solicitation.component.css'],
  providers: [FipeService]
})
export class SolicitationComponent implements OnInit {

  planRequest: PlanRequest = new PlanRequest();
  modelResponse: ModelResponse = new ModelResponse();
  brands: Brand[] = [];
  details: Detail = new Detail();

  constructor(private fipeService: FipeService) { }

  ngOnInit() {
    this.getBrands();
  }

  getBrands(): void {
    this.fipeService.getBrands().subscribe(brands => {
      this.brands = brands;
    })
  }

  getModels(brandId): void {
    this.fipeService.getModels(brandId).subscribe(models => {
      this.modelResponse = models;
    })
  }

  getDetails(brandId, modelId): void {
    this.fipeService.getDetails(brandId, modelId).subscribe(details => {
      this.details = details;
    })
  }

}
