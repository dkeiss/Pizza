var JsonInterfaces = (function () {
    function JsonInterfaces() {
    }
    return JsonInterfaces;
})();
var ProductCategory = (function () {
    function ProductCategory() {
    }
    return ProductCategory;
})();
var SubCategory = (function () {
    function SubCategory() {
    }
    return SubCategory;
})();
var Product = (function () {
    function Product() {
    }
    return Product;
})();
/*
declare module JsonInterfaces {

    export interface Product {
        name: string;
        description: string;
        price: string;
        priceSmall: string;
        priceMedium: string;
        priceLarge: string;
    }

    export interface SubCategory {
        name: string;
        products: Product[];
    }

    export interface ProductCategory {
        name: string;
        subCategories: SubCategory[];
    }

    export interface ProductData {
        productCategories: ProductCategory[];
    }

}
*/
//# sourceMappingURL=JsonInterfaces.js.map