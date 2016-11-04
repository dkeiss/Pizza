/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />
/// <reference path="IUser.ts" />


namespace WebApplication.Admin.UserManagement
{
    export class NewUserController
    {
        private _newUserContainer: JQuery = null;
        private _isAdminSelector: JQuery =  null;
        private _firstNameSelector: JQuery = null;
        private _lastNameSelector: JQuery = null;
        private _emailSelector: JQuery = null;
        private _discountSelector: JQuery = null;
        private _warningLabelSelector: JQuery = null;
        private _submitSelector: JQuery = null;

        private _cssInputError = "um-newUser-inputError";
        private _cssHiddenWarning = "um-newUser-hiddenWarning";
        private _cssShowWarning = "um-newUser-showWarning";

        constructor()
        {
            this._newUserContainer = $(UserManagementSelectors.newUserContainer);
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
            this._submitSelector.on("click", () => this.checkBeforeSubmit());
        }

        private checkBeforeSubmit()
        {
            let isOkay: boolean = true;


            if (this._emailSelector.val().trim().length == 0)
            {
                this._emailSelector.addClass(this._cssInputError);
                isOkay = false;
            }

            if (!NewUserController.isValidEmailAddress(this._emailSelector.val().trim()))
            {
                this._emailSelector.addClass(this._cssInputError)
                isOkay = false;
            }

            if (this._discountSelector.val().length == 0)
            {
                this._discountSelector.val(0);
            }


            if (isOkay)
            {
                this.saveAndResetInputField(newUser =>
                {
                    console.log("new User: " + JSON.stringify(newUser));
                    UserService.sendNewUser(newUser);
                });
            }
            else
            {
                this._warningLabelSelector.removeClass(this._cssHiddenWarning).addClass(this._cssShowWarning);
            }
        }

        private static isValidEmailAddress(email: string): boolean
        {
            const pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
            return pattern.test(email);
        }

        private saveAndResetInputField(returnNewUser: (newUser: IAddNewUser) => void): void
        {
            const newUser = new RequestNewUser();
            newUser.firstName = this._firstNameSelector.val();
            newUser.lastName = this._lastNameSelector.val();
            newUser.userName = this._emailSelector.val();
            newUser.discount = parseFloat(this._discountSelector.val());
            newUser.admin = this._isAdminSelector.is(":checked") ? true : false;

            this._newUserContainer.find("." + this._cssInputError).removeClass(this._cssInputError);
            this._warningLabelSelector.removeClass(this._cssShowWarning).addClass(this._cssHiddenWarning);
            this._firstNameSelector.val("");
            this._lastNameSelector.val("");
            this._emailSelector.val("");
            this._discountSelector.val("0");
            this._isAdminSelector.prop("checked", false);

            returnNewUser(newUser);
        }
    }

    export class RequestNewUser implements IAddNewUser
    {
        firstName: string;
        lastName: string;
        userName: string;
        discount: number;
        admin: boolean;
    }
}