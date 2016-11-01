/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="NewUserController.ts" />
/// <reference path="TableController.ts" />


namespace WebApplication.Admin.UserManagement
{
    $(document).ready(() =>
    {
        new UserFunction();
    });


    export class UserFunction
    {
        private _tableController: TableController = null;
        private _newUserController: NewUserController = null;

        constructor()
        {
            this._tableController = new TableController();
            this._tableController.start();

            this._newUserController = new NewUserController();
            this._newUserController.start();
        }
    }
}

