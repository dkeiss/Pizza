/// <reference path="IProductGroup.ts" />

interface IProductCategory{
    productCategoryId: number;
    name: string;
    productGroups: IProductGroups;
    creationDate: number
}

interface IProductCategories extends Array<IProductCategory>{}