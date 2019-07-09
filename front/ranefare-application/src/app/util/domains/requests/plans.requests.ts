export class PlanRequest {
    carBrand: number;
    carModel: String;
    carYear: String;
    zeroKm: Boolean;
    cpf: String;

    constructor(carBrand?: number, carModel?: String, carYear?: String, zeroKm?: Boolean, cpf?: String) {
        this.carBrand = carBrand;
        this.carModel = carModel;
        this.carYear = carYear;
        this.zeroKm = zeroKm;
        this.cpf = cpf;
    }
}