export class QuotationOption {
    name: String;
    price: number;
    coverageItems: Map<String, Boolean>;

    constructor(name?: String, price?: number, coverageItems?: Map<String, Boolean>) {
        this.name = name;
        this.price = price;
        this.coverageItems = coverageItems;
    }
}