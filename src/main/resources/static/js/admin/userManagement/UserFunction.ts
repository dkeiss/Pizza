/// <reference path="../../thirdParty/jquery.d.ts" />
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

        constructor()
        {
            this._tableController = new TableController();
        }
    }
}

