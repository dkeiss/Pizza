/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />
/// <reference path="IUser.ts" />


namespace WebApplication.Admin.UserManagement
{
    export class NewUserController
    {
        private _isAdminSelector: JQuery =  null;
        private _firstNameSelector: JQuery = null;
        private _lastNameSelector: JQuery = null;
        private _emailSelector: JQuery = null;
        private _discountSelector: JQuery = null;
        private _warningLabelSelector: JQuery = null;
        private _submitSelector: JQuery = null;

        constructor()
        {
            this._isAdminSelector = $(UserManagementSelectors.newUserIsAdmin);
            this._firstNameSelector = $(UserManagementSelectors.newUserFirstName);
            this._lastNameSelector = $(UserManagementSelectors.newUserLastName);
            this._emailSelector = $(UserManagementSelectors.newUserEMail);
            this._discountSelector = $(UserManagementSelectors.newUserDiscount);
            this._warningLabelSelector = $(UserManagementSelectors.newUserWarningLabel);
            this._submitSelector = $(UserManagementSelectors.newUserSubmit);
        }

        public start()
        {
            this._submitSelector.on("click", () => this.checkSubmit());
        }

        private checkSubmit()
        {

        }
    }

    export class NewUser implements IAddNewUser
    {
        firstName: string;
        lastName: string;
        discount: number;
        admin: boolean;
    }
}