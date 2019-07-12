export class QuotationOption {
    id: String;
    name: String;
    value: number;
    coverageItems: Map<String, Boolean>;

    constructor(name?: String, value?: number, coverageItems?: Map<String, Boolean>) {
        this.name = name;
        this.value = value;
        this.coverageItems = coverageItems;
    }
}