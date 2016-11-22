/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/ShowErrorDialog.ts" />
/// <reference path="IAddition.ts" />
/// <reference path="IProductCatalog.ts" />
/// <reference path="IOrder.ts" />

namespace WebApplication.UserOrder
{
    export class ShowAdditionDialog
    {
        private _additions: IAdditions = null;
        private _currentUser: IUser = null;
        private _showCurrentAdditions: IAdditionals = [];
        private _product: IProduct = null;
        private _productVariationId: number = null;
        private _priceSize: number = null;
        private _selectedAdditionalIds = [];

        private _cssShowDialog = "userOrder-additionalDialog-showContainer";
        private _cssSelectAdditions = "userOrder-additionalDialog-selectedAdditions";
        private _cssAdditionalTitleRed = "userOrder-additionalDialog-titleRed";

        private _additionalBox: JQuery = null;
        private _additionalDialog: JQuery = null;
        private _additionalBoxMenuSelectorDiv: JQuery = null;
        private _orderOverviewProduct: JQuery = null;
        private _orderOverviewAddition: JQuery = null;
        private _orderOverviewUserDiscount: JQuery = null;
        private _orderSubmit: JQuery = null;
        private _closeDialog: JQuery = null;

        constructor(additionalInfo: IAdditions, currentUser: IUser, product: IProduct, productVariationId: number, priceSize: number)
        {
            this._additionalBox = $(UserOrderSelector.additionalBox).html("");
            this._orderOverviewProduct = $(UserOrderSelector.orderOverviewText);
            this._orderOverviewAddition = $(UserOrderSelector.orderOverviewAddition);
            this._orderOverviewUserDiscount = $(UserOrderSelector.orderOverviewUserDiscount);
            this._orderSubmit = $(UserOrderSelector.orderSubmit);
            this._closeDialog = $(UserOrderSelector.closeDialog);

            this._additions = additionalInfo;
            this._currentUser = currentUser;
            this._product = product;
            this._productVariationId = productVariationId;
            this._priceSize = priceSize;
            this._showCurrentAdditions.length = 0;

            this.existProductIdInAdditions();
            this.setProductData();
            this.calcOrder();

            this._additionalBoxMenuSelectorDiv = $("." + UserOrderSelector.additionalBoxMenusSelector + " div");
            this._additionalDialog = $(UserOrderSelector.additionalDialog).addClass(this._cssShowDialog);
        }

        public start()
        {
            this._closeDialog.on("click", () => { this.closeDialog() });
            this._additionalBoxMenuSelectorDiv.on("click", event => { this.selectAdditions(event); });
            this._orderSubmit.on("click", () => { this.checkAndSubmitOrder(); });
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
                const additionalPrices = additionals[i].additionalPrices;
                let price = 0;

                if (additionalPrices.length == 1)
                {
                    price = additionalPrices[0].price;
                }
                else if (additionalPrices.length == 2 && this._priceSize == 3)
                {
                    price = additionalPrices[1].price;
                }
                else
                {
                    price = additionalPrices[this._priceSize].price;
                }


                htmlInput += `<div additionalId='${additionals[i].additionalId}'><span>${additionals[i].description}</span><span>${price}</span></div>`;

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
                .text(this.getProductPrice());

            this._orderOverviewUserDiscount
                .find("span")
                .last()
                .text(this._currentUser.discount);
        }

        private getProductPrice(): number
        {
            return this._product.productVariations.filter(item => item.productVariationId == this._productVariationId)[0].price;
        }


        /// EVENTS
        private calcOrder()
        {
            const productPrice: number = this.getProductPrice();
            const extraAddition = this.calcAddition();
            const userDiscount: number = this._currentUser.discount;

            const sum = parseFloat(((productPrice + extraAddition) - userDiscount).toFixed(2));
            this._orderSubmit.text( sum >= 0 ? sum : 0 );
        }

        private calcAddition(): number
        {
            let extraAddition: number = 0;

            for (let i = 0; i < this._selectedAdditionalIds.length; i++)
            {
                let tempObject = this._showCurrentAdditions.filter(item => item.additionalId == this._selectedAdditionalIds[i]);
                if (tempObject.length == 0) new ShowErrorDialog(null, "Load data error", "Additional not found - Please contact Administrator!");

                extraAddition += tempObject[0].additionalPrices[this._priceSize].price;
            }

            this._orderOverviewAddition
                .find("span")
                .last()
                .text( parseFloat(extraAddition.toFixed(2)) );

            return extraAddition;
        }

        private closeDialog(): void
        {
            this._additionalDialog.removeClass(this._cssShowDialog);
        }

        private selectAdditions(eventObject: JQueryEventObject): void
        {
            const target = $(eventObject.currentTarget);
            const duty = target.closest(`.${UserOrderSelector.additionalBoxMenusSelector}`).attr("duty");

            duty == "true" ? this.selectSingleAdditions(target) : this.selectMultiAdditions(target);
            this.calcOrder();
        }

        private selectSingleAdditions(target: JQuery): void
        {
            target
                .closest(`.${UserOrderSelector.additionalBoxMenusSelector}`)
                .find("div")
                .each( (index, element) => {
                    $(element).removeClass(this._cssSelectAdditions);
                });

            target.addClass(this._cssSelectAdditions);

            target
                .parent()
                .parent()
                .find("span.title")
                .first()
                .removeClass(this._cssAdditionalTitleRed);
        }

        private selectMultiAdditions(target: JQuery): void
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


        private checkAndSubmitOrder()
        {
            this._additionalBoxMenuSelectorDiv
                .parent()
                .each( (index, element) => {
                    if ($(element).attr("duty") == "true")
                    {
                        if ( $(element).find(`div.${this._cssSelectAdditions}`).length != 1)
                        {
                            $(element)
                                .parent()
                                .find("span.title")
                                .first()
                                .addClass(this._cssAdditionalTitleRed);
                        }
                    }
                });


            var sendOrder = new SendOrder();
            sendOrder.productId = this._product.productId;
            sendOrder.productVariationId = this._productVariationId;
            sendOrder.additionalIds = this._selectedAdditionalIds;

            OrderService.sendOrderForUser(sendOrder, onsuccess => { alert("Bestellt!") });
        }
    }

    export class SendOrder implements IOrder
    {
        productId: number;
        productVariationId: number;
        additionalIds: number[];
    }
}