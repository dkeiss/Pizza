/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../WebServiceAccess.ts" />
/// <reference path="../../Constants.ts" />
/// <reference path="IProductCatalog.ts" />


namespace WebApplication.Admin.Overview
{
    $(document).ready(() =>
    {
        new AdminFunction();
    });
    /*$(document).ready(() =>
    {
        $('#timepicker.startTime').timepicker( {
            hourText: 'Stunde',             // Define the locale text for "Hours"
            minuteText: 'Minute',         // Define the locale text for "Minute"
            amPmText: ['', ''],     // Display text for AM PM
            showAnim: 'blind'
        } );
    });*/

    export class AdminFunction
    {
        private _adminTimepicker: JQuery = null;
        private _adminTimepickerLabel: JQuery = null;
        private _productCatalog: IProductCatalog[] = null;

        constructor()
        {
            console.log("test2");
            this._adminTimepicker = $(AdminOverviewSelectors.adminTimepicker);
            this._adminTimepicker.on("change", () => this.validateTime());

            this._adminTimepickerLabel = $(AdminOverviewSelectors.adminTimepickerLabel);
            this._adminTimepickerLabel.hide();

            this.updatProductCatalog();
        }

        public validateTime() : void {
            console.log("test2");

            var isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])(:[0-5][0-9])?$/.test(this._adminTimepicker.val());

            if (isValid) {
                this._adminTimepicker.toggleClass("admin-inputField_time_error",false);
                this._adminTimepickerLabel.hide();
            } else {
                this._adminTimepicker.toggleClass("admin-inputField_time_error",true);
                this._adminTimepickerLabel.show();
            }


        }

        private updatProductCatalog(): void
        {
            AdminFunction.loadProductList(productCatalog =>
            {
                console.log("bulkorderloaded");
                this._productCatalog = productCatalog;

                for(let i = 0; i < this._productCatalog.length; i++)
                {
                    console.log(this._productCatalog[i].bulkOrderId);
                    console.log(this._productCatalog[i].catalogId);
                    console.log(this._productCatalog[i].name);
                    console.log(this._productCatalog[i].activeUntil);
                }
            });
        }

        public static loadProductList(onSuccess: (productCatalog: IProductCatalog[]) => void): void
        {
            console.log("loadingbulkorder");
            WebServiceAccess.ajaxGet(WebService.url + "bulkorder", onSuccess);
        }
    }




}



