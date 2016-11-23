/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/ShowErrorDialog.ts" />
/// <reference path="OrderService.ts" />
/// <reference path="IProductCatalog.ts" />

namespace WebApplication.UserOrder
{
    export class CreateProductCatalogHtml
    {
        private _productCatalog: IProductCatalog = null;
        private _products: IProducts = [];
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
            });

            this.createMenuHtml(this._productCatalog.productCategories);
            this.createProductCategories(this._productCatalog.productCategories);
        }

        public start(returnDataForAddition: (product: IProduct, productVariationId: number, priceSize: number) => void): void
        {
            this._menuClickSelectors = $(UserOrderSelector.menuClickSelectors);
            this._menuClickSelectors.on("click", event => { this.showHideCategories(event)} );
            $("." + UserOrderSelector.CellSelectors).on("click", event => { this.getDataForAddition(event, returnDataForAddition) });
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
                this._products.push(products[indexProducts]);

                productElements += `<tr>`;
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
                    if (priceVariations[indexVariation].name == "normal")
                        this._productPriceVariations[0] = true;

                    if (priceVariations[indexVariation].name == "klein")
                        this._productPriceVariations[1] = true;

                    if (priceVariations[indexVariation].name == "mittel")
                        this._productPriceVariations[2] = true;

                    if (priceVariations[indexVariation].name == "groß")
                        this._productPriceVariations[3] = true;

                    if (priceVariations[indexVariation].name == "")
                    {} // for Debug
                }
            }

            let returnVariations = "";
            if (this._productPriceVariations[0]) return "<th class='userOrder-price'></th>";
            if (this._productPriceVariations[1]) returnVariations += "<th class='userOrder-price'>klein</th>";
            if (this._productPriceVariations[2]) returnVariations += "<th class='userOrder-price'>mittel</th>";
            if (this._productPriceVariations[3]) returnVariations += "<th class='userOrder-price'>groß</th>";

            return returnVariations;
        }

        private getVariationPrices(variations: IProductVariations): string
        {
            if (this._productPriceVariations[0])
            {
                return `<td class='userOrder-price-cell'><div priceSize='0' productVariationId='${variations[0].productVariationId}' class='${UserOrderSelector.CellSelectors}'>${variations[0].price}</div></td>`;
            }

            let returnPriceElements = "";

            if (this._productPriceVariations[1])
            {
                const variation = variations.filter(item => item.name == "klein");
                if (variation.length > 0)
                {
                    returnPriceElements += `<td class='userOrder-price-cell'><div priceSize='0' productVariationId='${variation[0].productVariationId}' class='${UserOrderSelector.CellSelectors}'>${variation[0].price}</div></td>`;
                }
                else
                {
                    returnPriceElements += "<td></td>";
                }
            }

            if (this._productPriceVariations[2])
            {
                const variation = variations.filter(item => item.name == "mittel");
                if (variation.length > 0)
                {
                    returnPriceElements += `<td class='userOrder-price-cell'><div priceSize='1' productVariationId='${variation[0].productVariationId}' class='${UserOrderSelector.CellSelectors}'>${variation[0].price}</div></td>`;
                }
                else
                {
                    returnPriceElements += "<td></td>";
                }
            }

            if (this._productPriceVariations[3])
            {
                const variation = variations.filter(item => item.name == "groß");
                if (variation.length > 0)
                {
                    returnPriceElements += `<td class='userOrder-price-cell'><div priceSize='2' productVariationId='${variation[0].productVariationId}' class='${UserOrderSelector.CellSelectors}'>${variation[0].price}</div></td>`;
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

        private getDataForAddition(eventObject: JQueryEventObject, returnDataForAddition: (product: IProduct, productVariationId: number, priceSize: number) => void): void
        {
            const priceSize = eventObject.target.getAttribute("priceSize");
            const productVariationId = eventObject.target.getAttribute("productVariationId");

            const productId = $(eventObject.currentTarget)
                .closest("tr")
                .find("td")
                .first()
                .text();

            const product = this._products.filter(item => item.number == parseInt(productId))[0];

            returnDataForAddition(product, parseInt(productVariationId), parseInt(priceSize));
        }
    }
}
