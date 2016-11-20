/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="TableController.ts" />


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

        constructor()
        {
            console.log("constructor");
            this._cardOrderContainer = $(ShoppingCardSelectors.cardOrderContainer);
            this._cardOrderButton = $(ShoppingCardSelectors.cardOrderButton);
            this._tableController = new TableController();
            this._cardOrderButton.on("click", () => this.markOrderAsOrdered());
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
    }
}