import { Component, OnInit, Input } from '@angular/core';
import { QuotationOption } from '../util/domains/quotation-option.domain';

@Component({
  selector: 'plan-panel',
  templateUrl: './plan-panel.component.html',
  styleUrls: ['./plan-panel.component.css']
})
export class PlanPanelComponent implements OnInit {

  @Input()
  quotationPlan: QuotationOption;
  coverageItemsKeys: IterableIterator<String>;
  coverageItemsValues: IterableIterator<Boolean>;

  constructor() { }

  ngOnInit() {
    this.convertCoverageItems();
    console.log(this.quotationPlan)
  }

  convertCoverageItems(): void{
    this.coverageItemsKeys = this.quotationPlan.coverageItems.keys();
    this.coverageItemsValues = this.quotationPlan.coverageItems.values();
  }

}
