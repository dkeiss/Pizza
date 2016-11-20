/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="IAddition.ts" />

namespace WebApplication.UserOrder
{
    export class ShowAdditionDialog
    {
        private _additions: IAdditions = null;
        private _priceSize: number = null;
        private _productId: number = null;

        private _cssShowDialog = "userOrder-additionalDialog-showContainer";

        private _additionalDialog: JQuery = null;

        constructor(additionalInfo: IAdditions, priceSize: number, productId: number)
        {
            this._additions = additionalInfo;
            this._priceSize = priceSize;
            this._productId = productId;

            this.existProductIdInAdditions();

            this._additionalDialog = $(UserOrderSelector.additionalDialog).addClass(this._cssShowDialog);
        }

        public start()
        {

        }

        private existProductIdInAdditions()
        {
            const additions = this._additions;

            for(let i = 0; i < additions.length; i++)
            {
                let found = additions[i].productIds.filter(item => item == this._productId);

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
            $(UserOrderSelector.additionalBox).append(htmlInput);
        }
    }
}