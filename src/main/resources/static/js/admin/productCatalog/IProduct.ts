/// <reference path="IProductVariation.ts" />

interface IProduct {
    productId: number;
    number: number;
    name: string;
    description: string;
    productVariations: IProductVariations;
    creationDate: number;
}

interface IProducts extends Array<IProduct>{}