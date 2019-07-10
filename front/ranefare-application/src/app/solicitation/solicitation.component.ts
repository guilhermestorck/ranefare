import { Component, OnInit } from '@angular/core';
import { FipeService } from '../util/service/fipe.service';
import { PlanRequest } from '../util/domains/requests/plans.requests';
import { Brand } from '../util/domains/brand.domain';
import { ModelResponse } from '../util/domains/responses/model.response';
import { Detail } from '../util/domains/details.domain';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ValidationCPF } from '../util/validations/validation-cpf';

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

  cpfInvalid: Boolean;

  constructor(private fipeService: FipeService,
    private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.getBrands();
    this.cpfInvalid = false;
  }

  getBrands(): void {
    this.fipeService.getBrands().subscribe(brands => {
      this.brands = brands.brands;
    });
  }

  getModels(): void {
    this.fipeService.getModels(this.planRequest.carBrand).subscribe(models => {
      this.modelResponse = models;
    });
  }

  getDetails(): void {
    this.fipeService.getDetails(this.planRequest.carBrand, this.planRequest.carModel).subscribe(details => {
      this.planRequest.carYear = details.year;
    });
  }

  checkCPFIsValid() {
    if (!ValidationCPF.cpf(this.planRequest.cpf)) {
      this.cpfInvalid = true;
    } else {
      this.cpfInvalid = false;
    }
  }

}
