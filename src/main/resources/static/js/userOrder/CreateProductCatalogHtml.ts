/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="OrderService.ts" />
/// <reference path="IProductCatalog.ts" />

namespace WebApplication.UserOrder
{
    export class CreateProductCatalogHtml
    {
        private _productCatalog: IProductCatalog = null;

        private _menuSelector: JQuery = null;
        private _menuClickSelectors: JQuery = null;

        constructor()
        {
            this._menuSelector = $(UserOrderSelector.menuContainerSelector);

            OrderService.loadProductCatalog(getProductCatalog =>
            {
                this._productCatalog = getProductCatalog;
                console.log(this._productCatalog);
            });

            this.setMenuHtml(this._productCatalog.productCategories);
        }

        public start(): void
        {
            this._menuClickSelectors = $(UserOrderSelector.menuClickSelectors);
        }

        private setMenuHtml(menus: IProductCategories): void
        {
            let menuElement = "";

            for (let i = 0; i < menus.length; i++)
            {
                menuElement += `<div class="${UserOrderSelector.menuClickSelectors.replace(".", "")}">${menus[i].name}</div>`;
            }

            this._menuSelector.append(menuElement);
        }
    }
}
