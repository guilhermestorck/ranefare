import { Component, OnInit } from '@angular/core';
import { QuotationOption } from '../util/domains/quotation-option.domain';

@Component({
  selector: 'home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  quotationPlans: QuotationOption[] = null;

  constructor() { }

  ngOnInit() {
  }

  settingPlan(event) {
    this.quotationPlans = event;
  }

}
