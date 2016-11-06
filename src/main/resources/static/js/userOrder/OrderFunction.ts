/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />

namespace WebApplication.UserOrder
{
    $(document).ready(() =>
    {
        new UserOrder();
    });

    export class UserOrder
    {
        private _createProductCatalogHtml: CreateProductCatalogHtml = null;

        constructor()
        {
            this._createProductCatalogHtml = new CreateProductCatalogHtml();
        }
    }
}