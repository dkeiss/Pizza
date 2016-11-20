/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="IAddition.ts" />
/// <reference path="IProductCatalog.ts" />

namespace WebApplication.UserOrder
{
    export class ShowAdditionDialog
    {
        private _additions: IAdditions = null;
        private _priceSize: number = null;
        private _product: IProduct = null;

        private _cssShowDialog = "userOrder-additionalDialog-showContainer";

        private _additionalBox: JQuery = null;
        private _additionalDialog: JQuery = null;
        private _orderOverviewProduct: JQuery = null;
        private _orderOverviewAddition: JQuery = null;
        private _orderOverviewUserDiscount: JQuery = null;
        private _orderSubmit: JQuery = null;
        private _closeDialog: JQuery = null;

        constructor(additionalInfo: IAdditions, priceSize: number, product: IProduct)
        {
            this._additionalBox = $(UserOrderSelector.additionalBox).html("");
            this._orderOverviewProduct = $(UserOrderSelector.orderOverviewText);
            this._orderOverviewAddition = $(UserOrderSelector.orderOverviewAddition);
            this._orderOverviewUserDiscount = $(UserOrderSelector.orderOverviewUserDiscount);
            this._orderSubmit = $(UserOrderSelector.orderSubmit);
            this._closeDialog = $(UserOrderSelector.closeDialog);

            this._additions = additionalInfo;
            this._priceSize = priceSize;
            this._product = product;

            this.existProductIdInAdditions();
            this.setProductData();
            this.calcOrder();

            this._additionalDialog = $(UserOrderSelector.additionalDialog).addClass(this._cssShowDialog);
        }

        public start()
        {
            this._closeDialog.on("click", () => { this.closeDialog() });
        }

        private existProductIdInAdditions()
        {
            const additions = this._additions;

            for(let i = 0; i < additions.length; i++)
            {
                let found = additions[i].productIds.filter(item => item == this._product.number);

                if (found.length > 0)
                {
                    this.createHtmlAdditionals(additions[i].additionals);
                }
            }
        }

        private createHtmlAdditionals(additionals: IAdditionals)
        {
            let htmlInput = "<div class='userOrder-additionalDialog-additionBox'>";
            htmlInput += "<span class='title'>Extrazutaten / Extra Auswahl</span><div class='" + UserOrderSelector.additionalBoxMenus + "'>";

            for (let i = 0; i < additionals.length; i++)
            {
                htmlInput += "<div><span>" + additionals[i].description + "</span><span>" + additionals[i].additionalPrices[this._priceSize].price + "</span></div>";
            }

            htmlInput += "</div></div>";
            this._additionalBox.append(htmlInput);
        }

        private setProductData()
        {
            const product = this._product;

            this._orderOverviewProduct
                .find("span")
                .first()
                .text(product.number + ".  " + product.name);

            this._orderOverviewProduct
                .find("span")
                .last()
                .text(product.productVariations[this._priceSize].price);
        }


        /// EVENTS
        private calcOrder()
        {
            const productPrice: number = this._product.productVariations[this._priceSize].price;
            const extraAddition: number = parseFloat(this._orderOverviewAddition
                .find("span")
                .last()
                .text());

            const userDiscount: number = 0;

            this._orderSubmit.text((productPrice + extraAddition) - userDiscount);
        }

        private closeDialog()
        {
            this._additionalDialog.removeClass(this._cssShowDialog);
        }
    }
}