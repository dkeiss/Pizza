interface IProductVariation {
    productVariationId: number;
    name: string;
    price: number;
    creationDate?: number
}

interface IProductVariations extends Array<IProductVariation>{}

interface IProduct {
    productId: number;
    number: number;
    name: string;
    description: string;
    productVariations: IProductVariations;
    creationDate?: number;
}

interface IProducts extends Array<IProduct>{}

interface IProductGroup {
    productGroupId: number;
    name: string;
    products: IProducts;
    creationDate?: number;
}

interface IProductGroups extends Array<IProductGroup>{}

interface IProductCategory{
    productCategoryId: number;
    name: string;
    productGroups: IProductGroups;
    creationDate?: number
}

interface IProductCategories extends Array<IProductCategory>{}

interface IProductCatalog
{
    productCatalogId: number;
    name: string;
    productCategories: IProductCategories;
    creationDate?: number;
}

interface IProductCatalogInfo {
    productCatalogId: number;
    name: string;
    creationDate?: number;
}
