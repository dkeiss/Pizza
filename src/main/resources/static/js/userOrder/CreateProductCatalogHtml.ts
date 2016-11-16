/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="OrderService.ts" />
/// <reference path="IProductCatalog.ts" />

namespace WebApplication.UserOrder
{
    export class CreateProductCatalogHtml
    {
        private _productCatalog: IProductCatalog = null;
        private _productPriceVariations = [];

        private _menuSelector: JQuery = null;
        private _menuClickSelectors: JQuery = null;

        private _cssTableTitleTr = "userOrder-productTable-titleTr";

        constructor()
        {
            this._menuSelector = $(UserOrderSelector.menuContainerSelector);

            OrderService.loadProductCatalog(getProductCatalog =>
            {
                this._productCatalog = getProductCatalog;
                console.log(this._productCatalog);
            });

            this.createMenuHtml(this._productCatalog.productCategories);
            this.createProductCategories(this._productCatalog.productCategories);
        }

        public start(): void
        {
            this._menuClickSelectors = $(UserOrderSelector.menuClickSelectors);
            this._menuClickSelectors.on("click", event => { this.showHideCategories(event)} );
        }



        private createMenuHtml(menus: IProductCategories): void
        {
            if (!menus)
            {
                new ShowErrorDialog(null, "Load data error", "Could not load menu!");
                return;
            }

            let menuHtml = "";

            for (let i = 0; i < menus.length; i++)
            {
                menuHtml += `<div class="${UserOrderSelector.menuClickSelectors.replace(".", "")}" categorise="${i}">${menus[i].name}</div>`;
            }

            this._menuSelector.append(menuHtml);
        }

        // create categories from menu
        private createProductCategories(productCategories: IProductCategories): void
        {
            for (let indexCategorise = 0; indexCategorise < productCategories.length; indexCategorise++)
            {
                let categoriseSelect = $("." + UserOrderSelector.setCategoriesHtml)
                    .append(
                        $("<div>", {
                            "class": UserOrderSelector.categoriesShowContainer,
                            "categorise": indexCategorise
                        })
                    )
                    .children()
                    .last();

                this.createProductGroups(productCategories[indexCategorise].productGroups, categoriseSelect);
            }
        }

        // display product-box
        private createProductGroups(productGroups: IProductGroups, categoriseSelect: JQuery): void
        {
            for (let indexGroups = 0; indexGroups < productGroups.length; indexGroups++)
            {
                let groupSelect = categoriseSelect
                    .append(
                        $("<div>", {
                            "class": UserOrderSelector.groupSelectors,
                            "productGroups": indexGroups
                        })
                    )
                    .children()
                    .last();

                this.createProducts(productGroups[indexGroups].products, productGroups[indexGroups].name, groupSelect)
            }
        }

        // display products of product-box
        private createProducts(products: IProducts, productGroupName: string, groupSelect: JQuery): void
        {
            this._productPriceVariations = [false, false, false, false];

            let productElements = "<table class='productTable'>";
            productElements += "<tr class='" + this._cssTableTitleTr +"'><th colspan='2'>" + productGroupName + "</th>" + this.checkVariationsOfProducts(products) + "</tr>";

            for (let indexProducts = 0; indexProducts < products.length; indexProducts++)
            {
                productElements += `<tr productId="${products[indexProducts].productId}">`;
                    productElements += "<td class='userOrder-productTable-number'>" + products[indexProducts].number + "</td>";
                    productElements += "<td class='userOrder-productTable-text'><div>" + products[indexProducts].name + "</div>";
                    productElements += "<div>" + products[indexProducts].description + "</div></td>";

                    productElements += this.getVariationPrices(products[indexProducts].productVariations);

                productElements += "</tr>";
            }

            productElements += "</table>";
            groupSelect.append(productElements);
        }

        private checkVariationsOfProducts(products: IProducts): string
        {
            for (let indexProduct = 0; indexProduct < products.length; indexProduct++)
            {
                let priceVariations = products[indexProduct].productVariations;
                for (let indexVariation = 0; indexVariation < priceVariations.length; indexVariation++)
                {
                    if (priceVariations[indexVariation].name == "default") this._productPriceVariations[0] = true;
                    if (priceVariations[indexVariation].name == "small") this._productPriceVariations[1] = true;
                    if (priceVariations[indexVariation].name == "medium") this._productPriceVariations[2] = true;
                    if (priceVariations[indexVariation].name == "large") this._productPriceVariations[3] = true;
                }
            }

            let returnVariations = "";
            if (this._productPriceVariations[0]) return "<th class='userOrder-price'></th>";
            if (this._productPriceVariations[1]) returnVariations += "<th class='userOrder-price'>small</th>";
            if (this._productPriceVariations[2]) returnVariations += "<th class='userOrder-price'>medium</th>";
            if (this._productPriceVariations[3]) returnVariations += "<th class='userOrder-price'>large</th>";

            return returnVariations;
        }

        private getVariationPrices(variations: IProductVariations): string
        {
            if (this._productPriceVariations[0])
            {
                return "<td class='userOrder-price-cell'><div>" + variations[0].price + "</div></td>";
            }

            let returnPriceElements = "";

            if (this._productPriceVariations[1])
            {
                const variation = variations.filter(item => item.name == "small");
                if (variation.length > 0)
                {
                    returnPriceElements += "<td class='userOrder-price-cell'><div>" + variation[0].price + "</div></td>";
                }
                else
                {
                    returnPriceElements += "<td></td>";
                }
            }

            if (this._productPriceVariations[2])
            {
                const variation = variations.filter(item => item.name == "medium");
                if (variation.length > 0)
                {
                    returnPriceElements += "<td class='userOrder-price-cell'><div>" + variation[0].price + "</div></td>";
                }
                else
                {
                    returnPriceElements += "<td></td>";
                }
            }

            if (this._productPriceVariations[3])
            {
                const variation = variations.filter(item => item.name == "large");
                if (variation.length > 0)
                {
                    returnPriceElements += "<td class='userOrder-price-cell'><div>" + variation[0].price + "</div></td>";
                }
                else
                {
                    returnPriceElements += "<td></td>";
                }
            }

            return returnPriceElements;
        }


        // ################ EVENTS ################
        private showHideCategories(eventObject: JQueryEventObject)
        {
            const elementShow = "show";

            $("." + UserOrderSelector.categoriesShowContainer)
                .each( (index, element) =>
                {
                    if (eventObject.target.getAttribute("categorise") == $(element).attr("categorise"))
                    {
                        $(element).addClass(elementShow);
                    }
                    else
                    {
                        $(element).removeClass(elementShow);
                    }
                });
        }
    }
}
