export class Detail {
    brandId: number;
    brandName: String;
    fuel: String;
    modelId: String;
    modelName: String;
    price: String;
    year: String;

    constructor(brandId?: number, brandName?: String, fuel?: String, modelId?: String, modelName?: String, price?: String, year?: String) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.fuel = fuel;
        this.modelId = modelId;
        this.modelName = modelName;
        this.price = price;
        this.year = year;
    }
}