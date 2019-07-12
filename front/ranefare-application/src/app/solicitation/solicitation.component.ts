import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Brand } from '../util/domains/brand.domain';
import { Detail } from '../util/domains/details.domain';
import { QuotationOption } from '../util/domains/quotation-option.domain';
import { PlanRequest } from '../util/domains/requests/plans.requests';
import { ModelResponse } from '../util/domains/responses/model.response';
import { FipeService } from '../util/service/fipe.service';
import { ValidationCPF } from '../util/validations/validation-cpf';

@Component({
  selector: 'solicitation',
  templateUrl: './solicitation.component.html',
  styleUrls: ['./solicitation.component.css'],
  providers: [FipeService]
})
export class SolicitationComponent implements OnInit {

  cpfInvalid: Boolean;

  planRequest: PlanRequest = new PlanRequest();
  modelResponse: ModelResponse = new ModelResponse();
  details: Detail = new Detail();

  brands: Brand[] = [];
  quotationPlans: QuotationOption[] = null;

  constructor(private fipeService: FipeService) { }

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

  continue() {
    // TODO chamar serviço de quotation para requisitar os planos
    this.quotationPlans = [];
    this.quotationPlans.push(new QuotationOption("Básico", 129.99, new Map([["Roubo, Furto e Incêndio", true], ["Alagamento", true], ["Colisão - Só perda Total", false], ["Colisão - Qualquer batida", false]])));
    this.quotationPlans.push(new QuotationOption("Mediano", 169.99, new Map([["Roubo, Furto e Incêndio", true], ["Alagamento", true], ["Colisão - Só perda Total", true], ["Colisão - Qualquer batida", false]])));
    this.quotationPlans.push(new QuotationOption("Plus", 199.99, new Map([["Roubo, Furto e Incêndio", true], ["Alagamento", true], ["Colisão - Só perda Total", true], ["Colisão - Qualquer batida", true]])));
  }

}
