import { QuotationOption } from '../quotation-option.domain';

export class PlansResponse {
    plans: QuotationOption[];

    constructor(plans: QuotationOption[]) {
        this.plans = plans;
    }
}