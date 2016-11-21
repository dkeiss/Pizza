/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="IAddition.ts" />
/// <reference path="IOrder.ts" />
/// <reference path="ShowAdditionDialog.ts" />

namespace WebApplication.UserOrder
{
    $(document).ready(() =>
    {
        new UserOrder();
    });

    export class UserOrder
    {
        private _createProductCatalogHtml: CreateProductCatalogHtml = null;
        private _additional: IAdditions = null;
        private _currentUser: IUser = null;

        constructor()
        {
            this._createProductCatalogHtml = new CreateProductCatalogHtml();
            this._createProductCatalogHtml.start( (priceSize, product) =>
            {
                if (this._additional == null) this.loadAddition();
                if (this._currentUser == null) this.loadUser();

                var showAdditionDialog = new ShowAdditionDialog(this._additional, this._currentUser, priceSize, product);
                showAdditionDialog.start();

            });
        }

        private loadAddition()
        {
            OrderService.loadAdditionInfo( getAddition => {
                this._additional = getAddition;
            });
        }

        private loadUser()
        {
            OrderService.loadUserInfo( getUser => {
                this._currentUser = getUser;
            });
        }
    }
}