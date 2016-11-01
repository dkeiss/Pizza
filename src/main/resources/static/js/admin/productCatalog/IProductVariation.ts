interface IProductVariation {
    productVariationId: number;
    name: string;
    price: number;
    creationDate: number
}

interface IProductVariations extends Array<IProductVariation>{}