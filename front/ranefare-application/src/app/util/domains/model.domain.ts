export class Model {
    modelId: number;
    modelName: String;

    constructor(modelId?: number, modelName?: String) {
        this.modelId = modelId;
        this.modelName = modelName;
    }
}