/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../share/Constants.ts" />
/// <reference path="IAdmin.ts" />
/// <reference path="AdminService.ts" />



import AdminService = WebApplication.Admin.Overview.AdminService;
class ShowInputDialog
{
    private _inputDialogContainer: JQuery = null;
    private _inputDialogCompanyInput: JQuery = null;
    private _inputDialogFirstNameInput: JQuery = null;
    private _inputDialogLastNameInput: JQuery = null;
    private _inputDialogStreetInput: JQuery = null;
    private _inputDialogNumberInput: JQuery = null;
    private _inputDialogZipInput: JQuery = null;
    private _inputDialogCityInput: JQuery = null;
    private _inputDialogPhoneInput: JQuery = null;
    private _inputDialogEMailInput: JQuery = null;
    private _inputDialogCommentArea: JQuery = null;
    private _inputDialogAcceptButton: JQuery = null;
    private _inputDialogCancelButton: JQuery = null;
    private _inputDialogWarningLabel: JQuery = null;

    private _currentDeliveryData: IDeliveryData = null;

    private _cssShowContainer = "admin-inputDialog-showContainer";
    //private _cssShowInputButton = "core-inputDialog-showButton";


    constructor()
    {
        this._inputDialogContainer = $(InputDialogSelectors.inputDialogContainer);
        this._inputDialogCompanyInput = $(InputDialogSelectors.inputDialogCompanyInput);
        this._inputDialogFirstNameInput = $(InputDialogSelectors.inputDialogFirstNameInput);
        this._inputDialogLastNameInput = $(InputDialogSelectors.inputDialogLastNameInput);
        this._inputDialogStreetInput = $(InputDialogSelectors.inputDialogStreetInput);
        this._inputDialogNumberInput = $(InputDialogSelectors.inputDialogNumberInput);
        this._inputDialogZipInput = $(InputDialogSelectors.inputDialogZipInput);
        this._inputDialogCityInput = $(InputDialogSelectors.inputDialogCityInput);
        this._inputDialogPhoneInput = $(InputDialogSelectors.inputDialogPhoneInput);
        this._inputDialogEMailInput = $(InputDialogSelectors.inputDialogEMailInput);
        this._inputDialogCommentArea = $(InputDialogSelectors.inputDialogCommentArea);
        this._inputDialogAcceptButton = $(InputDialogSelectors.inputDialogAcceptButton);
        this._inputDialogCancelButton = $(InputDialogSelectors.inputDialogCancelButton);
        this._inputDialogWarningLabel = $(InputDialogSelectors.inputDialogWarningLabel);

        this._inputDialogContainer.addClass(this._cssShowContainer);
        this._inputDialogWarningLabel.hide();
        /*this._container = this._inputDialogContainer.addClass(this._cssShowContainer);*/
        this._inputDialogCancelButton.on("click", () => {
            this._inputDialogContainer.removeClass(this._cssShowContainer);
            this._inputDialogContainer.find("input").val('');
            this._inputDialogContainer.find("input").removeClass("admin-inputField-error");
        });
        this._inputDialogAcceptButton.on("click", () => this.saveDeliveryData());

        this.getDeliveryData();
    }

    private getDeliveryData(): void {
        AdminService.getDeliveryData(deliveryData =>
            {
                this._currentDeliveryData = deliveryData;
                this._inputDialogCompanyInput.val(this._currentDeliveryData.company);
                this._inputDialogFirstNameInput.val(this._currentDeliveryData.firstName);
                this._inputDialogLastNameInput.val(this._currentDeliveryData.lastName);
                this._inputDialogStreetInput.val(this._currentDeliveryData.street);
                this._inputDialogNumberInput.val(this._currentDeliveryData.number);
                this._inputDialogZipInput.val(this._currentDeliveryData.zipCode);
                this._inputDialogCityInput.val(this._currentDeliveryData.city);
                this._inputDialogPhoneInput.val(this._currentDeliveryData.phone);
                this._inputDialogEMailInput.val(this._currentDeliveryData.eMail);
                this._inputDialogCommentArea.val(this._currentDeliveryData.additionalInformation);
            }
        );
    }

    private saveDeliveryData(): void {
        console.log(this.validateInputFields());
        if(!this.validateInputFields()){
            this._inputDialogWarningLabel.show();
        } else {
            this._inputDialogWarningLabel.hide();

            this._currentDeliveryData.company = this._inputDialogCompanyInput.val();
            this._currentDeliveryData.firstName = this._inputDialogFirstNameInput.val();
            this._currentDeliveryData.lastName = this._inputDialogLastNameInput.val();
            this._currentDeliveryData.street = this._inputDialogStreetInput.val();
            this._currentDeliveryData.number = this._inputDialogNumberInput.val();
            this._currentDeliveryData.zipCode = this._inputDialogZipInput.val();
            this._currentDeliveryData.city = this._inputDialogCityInput.val();
            this._currentDeliveryData.phone = this._inputDialogPhoneInput.val();
            this._currentDeliveryData.eMail = this._inputDialogEMailInput.val();
            this._currentDeliveryData.additionalInformation = this._inputDialogCommentArea.val();

            AdminService.saveDeliveryData(this._currentDeliveryData, success =>
                {
                    this._inputDialogContainer.removeClass(this._cssShowContainer);
                    this._inputDialogContainer.find("input").val('');
                }
            );
         }
    }

    private validateInputFields(): boolean {
        let valid = true;
        const emailPattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;

        this._inputDialogFirstNameInput.toggleClass("admin-inputField-error", this._inputDialogFirstNameInput.val().length == 0);
        this._inputDialogLastNameInput.toggleClass("admin-inputField-error", this._inputDialogLastNameInput.val().length == 0);
        this._inputDialogStreetInput.toggleClass("admin-inputField-error", this._inputDialogStreetInput.val().length == 0);
        this._inputDialogNumberInput.toggleClass("admin-inputField-error", this._inputDialogNumberInput.val().length == 0);
        this._inputDialogZipInput.toggleClass("admin-inputField-error", this._inputDialogZipInput.val().length == 0);
        this._inputDialogCityInput.toggleClass("admin-inputField-error", this._inputDialogCityInput.val().length == 0);
        this._inputDialogPhoneInput.toggleClass("admin-inputField-error", this._inputDialogPhoneInput.val().length == 0);
        this._inputDialogEMailInput.toggleClass("admin-inputField-error", !emailPattern.test(this._inputDialogEMailInput.val()));

        return this._inputDialogContainer.find(".admin-inputField-error").length == 0;
    }
}
