/// <reference path="IProductCategory.ts" />

interface IProductCatalog
{
    productCatalogId: number;
    name: string;
    productCategories: IProductCategories;
    creationDate: number;
}
