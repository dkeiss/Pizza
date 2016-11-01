/// <reference path="IProduct.ts" />

interface IProductGroup {
    productGroupId: number;
    name: string;
    products: IProducts;
    creationDate: number;
}

interface IProductGroups extends Array<IProductGroup>{}
