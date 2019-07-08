import { Model } from '../model.domain';

export class ModelResponse {
    brandId: number;
    models: Model[];

    constructor(brandId?: number, models?: Model[]) {
        this.brandId = brandId;
        this.models = models;
    }
}