/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="TableController.ts" />
/// <reference path="ShoppingCardService.ts" />
/// <reference path="../overview/IAdmin.ts" />


namespace WebApplication.Admin.ShoppingCard
{
    $(document).ready(() =>
    {
        new ShoppingCardFunction();
    });



    export class ShoppingCardFunction
    {
        private _tableController: TableController = null;
        private _cardOrderContainer: JQuery = null;
        private _cardOrderButton: JQuery = null;
        private _cardPrintControlSheetButton: JQuery = null;
        private _cardPrintOrderSheetButton: JQuery = null;


        private _cardDeliveryDataCompanySpan: JQuery = null;
        private _cardDeliveryDataFirstNameSpan: JQuery = null;
        private _cardDeliveryDataLastNameSpan: JQuery = null;
        private _cardDeliveryDataStreetAddressSpan: JQuery = null;
        private _cardDeliveryDataZipTownSpan: JQuery = null;
        private _cardDeliveryDataAdditionalInfoSpan: JQuery = null;
        private _cardDeliveryDataPhoneSpan: JQuery = null;
        private _cardDeliveryDataEMailSpan: JQuery = null;

        private _currentDeliveryData: IDeliveryData = null;

        constructor()
        {
            this._cardOrderContainer = $(ShoppingCardSelectors.cardOrderContainer);
            this._cardOrderButton = $(ShoppingCardSelectors.cardOrderButton);
            this._cardPrintControlSheetButton = $(ShoppingCardSelectors.cardPrintControlSheetButton);
            this._cardPrintOrderSheetButton = $(ShoppingCardSelectors.cardPrintOrderSheetButton);

            this._cardDeliveryDataCompanySpan = $(ShoppingCardSelectors.cardDeliveryDataCompanySpan);
            this._cardDeliveryDataFirstNameSpan = $(ShoppingCardSelectors.cardDeliveryDataFirstNameSpan);
            this._cardDeliveryDataLastNameSpan = $(ShoppingCardSelectors.cardDeliveryDataLastNameSpan);
            this._cardDeliveryDataStreetAddressSpan = $(ShoppingCardSelectors.cardDeliveryDataStreetAddressSpan);
            this._cardDeliveryDataZipTownSpan = $(ShoppingCardSelectors.cardDeliveryDataZipTownSpan);
            this._cardDeliveryDataAdditionalInfoSpan = $(ShoppingCardSelectors.cardDeliveryDataAdditionalInfoSpan);
            this._cardDeliveryDataPhoneSpan = $(ShoppingCardSelectors.cardDeliveryDataPhoneSpan);
            this._cardDeliveryDataEMailSpan = $(ShoppingCardSelectors.cardDeliveryDataEMailSpan);

            this.getDeliveryData();

            this._tableController = new TableController();
            this._cardOrderButton.on("click", () => this.markOrderAsOrdered());
            this._cardPrintControlSheetButton.on("click", () => window.print());
        }

        private markOrderAsOrdered(): void {
            console.log("markOrderAsOrdered");
            this._cardOrderButton.text("Sammelbestellung ist eingetroffen");
            this._cardOrderButton.off();
            this._cardOrderButton.on("click", () => this.markOrderAsArived());

        }

        private markOrderAsArived(): void {
            console.log("markOrderAsArived");
            this._cardOrderButton.off();
            this._cardOrderButton.text("Sammelbestellung abschließen");
            this._cardOrderButton.on("click", () => this.closeOrder());

        }

        private closeOrder(): void {
            console.log("closeOrder");

        }

        private getDeliveryData(): void {
            ShoppingCardService.getDeliveryData(deliveryData =>
                {
                    this._currentDeliveryData = deliveryData;
                    this._cardDeliveryDataCompanySpan.text(this._currentDeliveryData.deliveryServiceName);
                    this._cardDeliveryDataFirstNameSpan.text(this._currentDeliveryData.firstName);
                    this._cardDeliveryDataLastNameSpan.text(this._currentDeliveryData.lastName);
                    this._cardDeliveryDataStreetAddressSpan.text(this._currentDeliveryData.street + " " + this._currentDeliveryData.streetNumber);
                    this._cardDeliveryDataZipTownSpan.text(this._currentDeliveryData.postalCode + " " + this._currentDeliveryData.town);
                    this._cardDeliveryDataPhoneSpan.text(this._currentDeliveryData.telephoneNumber);
                    this._cardDeliveryDataEMailSpan.text(this._currentDeliveryData.emailAddress);
                    this._cardDeliveryDataAdditionalInfoSpan.text(this._currentDeliveryData.additionalInfos);
                }
            );
        }
    }
}