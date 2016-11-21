/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/ShowErrorDialog.ts" />
/// <reference path="IAddition.ts" />
/// <reference path="IProductCatalog.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.UserOrder
{
    export class ShowAdditionDialog
    {
        private _additions: IAdditions = null;
        private _currentUser: IUser = null;
        private _showCurrentAdditions: IAdditionals = [];
        private _priceSize: number = null;
        private _product: IProduct = null;
        private _selectedAdditionalIds = [];

        private _cssShowDialog = "userOrder-additionalDialog-showContainer";
        private _cssSelectAdditions = "userOrder-additionalDialog-selectedAdditions";

        private _additionalBox: JQuery = null;
        private _additionalDialog: JQuery = null;
        private _additionalBoxMenuSelector: JQuery = null;
        private _orderOverviewProduct: JQuery = null;
        private _orderOverviewAddition: JQuery = null;
        private _orderOverviewUserDiscount: JQuery = null;
        private _orderSubmit: JQuery = null;
        private _closeDialog: JQuery = null;

        constructor(additionalInfo: IAdditions, currentUser: IUser, priceSize: number, product: IProduct)
        {
            this._additionalBox = $(UserOrderSelector.additionalBox).html("");
            this._orderOverviewProduct = $(UserOrderSelector.orderOverviewText);
            this._orderOverviewAddition = $(UserOrderSelector.orderOverviewAddition);
            this._orderOverviewUserDiscount = $(UserOrderSelector.orderOverviewUserDiscount);
            this._orderSubmit = $(UserOrderSelector.orderSubmit);
            this._closeDialog = $(UserOrderSelector.closeDialog);

            this._additions = additionalInfo;
            this._currentUser = currentUser;
            this._priceSize = priceSize;
            this._product = product;
            //if (this._currentAdditions != null)
                this._showCurrentAdditions.length = 0;

            this.existProductIdInAdditions();
            this.setProductData();
            this.calcOrder();

            //this._additionalBoxMenuSelector = $(UserOrderSelector.additionalBoxMenusSelector + " div");
            this._additionalBoxMenuSelector = $(`.userOrder-additionalDialog_additionBoxMenus div`);
            this._additionalDialog = $(UserOrderSelector.additionalDialog).addClass(this._cssShowDialog);
        }

        public start()
        {
            this._closeDialog.on("click", () => { this.closeDialog() });
            this._additionalBoxMenuSelector.on("click", event => { this.selectAdditions(event); });
        }

        private existProductIdInAdditions()
        {
            const additions = this._additions;

            for(let i = 0; i < additions.length; i++)
            {
                let found = additions[i].productIds.filter(item => item == this._product.number);

                if (found.length > 0)
                {
                    this.createHtmlAdditionals(additions[i].additionals, additions[i].duty);
                }
            }
        }

        private createHtmlAdditionals(additionals: IAdditionals, duty: boolean)
        {
            let htmlInput = "<div class='userOrder-additionalDialog-additionBox'>";

            duty ?
                htmlInput += `<span class='title'>Bitte auswählen ...</span>` :
                htmlInput += `<span class='title'>Extrazutaten / Extra Auswahl</span>`;


            htmlInput += `<div duty='${duty}' class='${UserOrderSelector.additionalBoxMenus} ${UserOrderSelector.additionalBoxMenusSelector}'>`;

            for (let i = 0; i < additionals.length; i++)
            {
                htmlInput += `<div additionalId='${additionals[i].additionalId}'><span>${additionals[i].description}</span><span>${additionals[i].additionalPrices[this._priceSize].price}</span></div>`;

                this._showCurrentAdditions.push(additionals[i]);
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

            this._orderOverviewAddition
                .find("span")
                .last()
                .text(this.calcAddition());

            this._orderOverviewUserDiscount
                .find("span")
                .last()
                .text(this._currentUser.discount);
        }


        /// EVENTS
        private calcOrder()
        {
            const productPrice: number = this._product.productVariations[this._priceSize].price;
            const extraAddition = this.calcAddition();
            const userDiscount: number = this._currentUser.discount;

            this._orderSubmit.text((productPrice + extraAddition) - userDiscount);
        }

        private calcAddition(): number
        {
            let extraAddition: number = 0;

            for (let i = 0; i < this._selectedAdditionalIds.length; i++)
            {
                let tempObject = this._showCurrentAdditions.filter(item => item.additionalId == this._selectedAdditionalIds[i]);
                if (tempObject.length == 0) new ShowErrorDialog(null, "Load data error", "Could not load menu!");

                extraAddition += tempObject[0].additionalPrices[this._priceSize].price;
            }

            return extraAddition;
        }

        private closeDialog()
        {
            this._additionalDialog.removeClass(this._cssShowDialog);
        }

        private selectAdditions(eventObject: JQueryEventObject)
        {
            const target = $(eventObject.currentTarget);
            const duty = target.closest(`.${UserOrderSelector.additionalBoxMenusSelector}`).attr("duty");

            duty == "true" ? this.selectSingleAdditions(target) : this.selectMultiAdditions(target);
        }

        private selectSingleAdditions(target: JQuery)
        {

        }

        private selectMultiAdditions(target: JQuery)
        {
            target.hasClass(this._cssSelectAdditions) ? target.removeClass(this._cssSelectAdditions) : target.addClass(this._cssSelectAdditions);

            const additionalId = ShowAdditionDialog.getAdditionalIdFromDiv(target);

            this._selectedAdditionalIds.indexOf(additionalId) >= 0 ?
                this._selectedAdditionalIds.splice(this._selectedAdditionalIds.indexOf(additionalId), 1) :
                this._selectedAdditionalIds.push(additionalId);


        }

        private static getAdditionalIdFromDiv(target: JQuery): number
        {
            return parseInt(target.attr("additionalId"));
        }
    }
}