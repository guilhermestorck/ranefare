import { Component, OnInit, Input } from '@angular/core';
import { QuotationOption } from '../util/domains/quotation-option.domain';

@Component({
  selector: 'plans-page-container',
  templateUrl: './plans-page-container.component.html',
  styleUrls: ['./plans-page-container.component.css']
})
export class PlansPageContainerComponent implements OnInit {
  
  @Input()
  quotationPlans: QuotationOption[] = [];

  constructor() { }

  ngOnInit() {
   
  }

}
