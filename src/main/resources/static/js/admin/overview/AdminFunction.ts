/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="AdminService.ts" />
/// <reference path="../../share/Constants.ts" />
/// <reference path="../productCatalog/IProductCatalog.ts" />
/// <reference path="IAdmin.ts" />

/// <reference path="ShowDeliveryDataDialog.ts" />

namespace WebApplication.Admin.Overview
{
    $(document).ready(() =>
    {
        new AdminFunction();
    });

    export class AdminFunction
    {
        private _adminCardButton: JQuery = null;
        private _adminTimePicker: JQuery = null;
        private _adminTimePickerLabel: JQuery = null;
        private _adminUploadCatalogButton: JQuery = null;
        private _adminCatalogPostfixLabel: JQuery = null;
        private _adminCatalogPrefixLabel: JQuery = null;
        private _adminCatalogComboBox: JQuery = null;
        private _adminActivateCatalogButton: JQuery = null;
        private _adminFileInput: JQuery = null;
        private _adminGetSimpleCatalogButton: JQuery = null;
        private _adminGetExpertCatalogButton: JQuery = null;

        private _adminCardDiv: JQuery = null;
        private _adminUserManagementDiv: JQuery = null;
        private _adminBulkOrderDiv: JQuery = null;
        private _adminPrintDiv: JQuery = null;
        private _adminCatalogDiv: JQuery = null;

        private _adminOpenInputDialogButton: JQuery = null;

        private _bulkOrder: IBulkOrder[] = null;
        private _currentBulkOrder: IBulkOrder = null;

        private _availableProductCatalogs: IProductCatalogInfo[] = null;

        private _errorResponse: IErrorResponse = null;



        constructor()
        {
            console.log("test2");
            this._adminCardButton = $(AdminOverviewSelectors.adminCardButton);
            this._adminUploadCatalogButton = $(AdminOverviewSelectors.adminUploadCatalogButton);
            this._adminTimePicker = $(AdminOverviewSelectors.adminTimePicker);
            this._adminCatalogPrefixLabel = $(AdminOverviewSelectors.adminCatalogPrefixLabel);
            this._adminCatalogPostfixLabel = $(AdminOverviewSelectors.adminCatalogPostfixLabel);
            this._adminCatalogComboBox = $(AdminOverviewSelectors.adminCatalogComboBox);
            this._adminTimePickerLabel = $(AdminOverviewSelectors.adminTimePickerLabel);
            this._adminActivateCatalogButton = $(AdminOverviewSelectors.adminActivateCatalogButton);
            this._adminCardDiv = $(AdminOverviewSelectors.adminCardDiv);
            this._adminUserManagementDiv = $(AdminOverviewSelectors.adminUserManagementDiv);
            this._adminBulkOrderDiv = $(AdminOverviewSelectors.adminBulkOrderDiv);
            this._adminPrintDiv = $(AdminOverviewSelectors.adminPrintDiv);
            this._adminCatalogDiv = $(AdminOverviewSelectors.adminCatalogDiv);
            this._adminFileInput = $(AdminOverviewSelectors.adminFileInput);
            this._adminOpenInputDialogButton = $(AdminOverviewSelectors.adminOpenInputDialogButton);
            this._adminGetSimpleCatalogButton = $(AdminOverviewSelectors.adminGetSimpleCatalogButton);
            this._adminGetExpertCatalogButton = $(AdminOverviewSelectors.adminGetExpertCatalogButton);

            this.resetGUI();
            this.getProductCatalogs();
            this.getCurrentBulkOrder();


            this._adminUploadCatalogButton.on("click", () => {this._adminFileInput.trigger("click");});
            this._adminFileInput.on("change", () => this.uploadProductCatalog());
            this._adminOpenInputDialogButton.on("click",function(){new ShowInputDialog()});
            this._adminGetSimpleCatalogButton.on("click", () => {window.location.replace('rest/admin/productcatalog/download/simple');});
            this._adminGetExpertCatalogButton.on("click", () => {window.location.replace('rest/admin/productcatalog/download/expert');});
        }

        private resetGUI(): void {
            this._adminFileInput.hide();
            this._adminCardButton.off();
            this._adminCardButton.hover(function(){$(this).text("Derzeit ist keine Sammelbestellung aktiv");},function(){$(this).text("Warenkorb");});
            this._adminCardButton.removeClass("admin-button-enabled").addClass("admin-button-disabled");
            this._adminCardButton.on("click",function(){return false});
            this._adminTimePicker.hide();
            this._adminCatalogComboBox.hide();
            this._adminTimePickerLabel.hide();
            this._adminCatalogComboBox.empty();
            this._availableProductCatalogs = null;
            this._adminTimePicker.val("");
            this._adminCatalogPostfixLabel.show();
            this._currentBulkOrder = null;
            this._adminActivateCatalogButton.off();
            this._adminActivateCatalogButton.on("click", () => this.activateBulkOrder());
            this._adminActivateCatalogButton.text("Aktivieren");
        }

        public validateTime() : boolean {
            this._adminActivateCatalogButton.off();
            console.log("time");

            let isValid = /^([0-1]?[0-9]|2[0-4]):([0-5][0-9])$/.test(this._adminTimePicker.val());

            if (isValid) {
                this._adminTimePicker.removeClass("core-inputField-error");
                this._adminActivateCatalogButton.removeClass("admin-submitButton-disabled").addClass("admin-submitButton-enabled");
                this._adminTimePickerLabel.hide();
                this._adminActivateCatalogButton.on("click", () => this.activateBulkOrder());
            } else {
                this._adminTimePicker.addClass("core-inputField-error");
                this._adminActivateCatalogButton.addClass("admin-submitButton-disabled").removeClass("admin-submitButton-enabled");
                this._adminTimePickerLabel.text("Ungültige Uhrzeit");
                this._adminTimePickerLabel.show();
                this._adminBulkOrderDiv.addClass("admin-failure-blink");
                setTimeout(() =>
                {
                    this._adminBulkOrderDiv.removeClass("admin-failure-blink");
                }, 500);
            }

            return isValid;
        }

        private deaktivateBulkOrder(): void{
            this._adminCatalogPrefixLabel.addClass("hide");
            this._adminCatalogPostfixLabel.addClass("hide");
            this._adminTimePicker.addClass("hide");
            this._adminCatalogComboBox.addClass("hide");
            this._adminActivateCatalogButton.addClass("hide");
            this._adminBulkOrderDiv.addClass("admin-loading");

            AdminService.deactivateBulkOrder(this._currentBulkOrder.bulkOrderId, success => {
                if (success){
                    this.resetGUI();
                    this.getProductCatalogs();
                    this.getCurrentBulkOrder();
                    this._adminBulkOrderDiv.addClass("admin-success-blink");
                    setTimeout(() =>
                    {
                        this._adminBulkOrderDiv.removeClass("admin-success-blink");
                    }, 500);

                    this._adminCatalogPrefixLabel.removeClass("hide");
                    this._adminCatalogPostfixLabel.removeClass("hide");
                    this._adminTimePicker.removeClass("hide");
                    this._adminCatalogComboBox.removeClass("hide");
                    this._adminActivateCatalogButton.removeClass("hide");
                    this._adminBulkOrderDiv.removeClass("admin-loading");
                }}
            );

        }

        private activateBulkOrder(): void{
            if(this.validateTime()){
                this._adminActivateCatalogButton.off();
                let time = this._adminTimePicker.val().split(":");
                let endDate = new Date();
                endDate.setHours(time[0],time[1]);


                let _newBulkOrder: IBulkOrder = {
                    bulkOrderId: -1,
                    catalogId: this._availableProductCatalogs[this._adminCatalogComboBox.val()].productCatalogId,
                    name: this._availableProductCatalogs[this._adminCatalogComboBox.val()].name,
                    activeUntil: endDate.getTime()
                };

                this._adminCatalogPrefixLabel.addClass("hide");
                this._adminCatalogPostfixLabel.addClass("hide");
                this._adminTimePicker.addClass("hide");
                this._adminCatalogComboBox.addClass("hide");
                this._adminActivateCatalogButton.addClass("hide");
                this._adminBulkOrderDiv.addClass("admin-loading");

                AdminService.activateBulkOrder(_newBulkOrder,
                    bulkOrder => {
                        this._currentBulkOrder = bulkOrder;

                        //this._adminTimePicker.removeClass("core-inputField-error");
                        //this._adminActivateCatalogButton.removeClass("admin-submitButton-disabled").addClass("admin-submitButton-enabled");

                        //this._adminActivateCatalogButton.on("click", () => this.activateBulkOrder());
                        //this._adminTimePickerLabel.hide();

                        this.resetGUI();
                        this.getCurrentBulkOrder();
                        this._adminBulkOrderDiv.addClass("admin-success-blink");
                        setTimeout(() =>
                        {
                            this._adminBulkOrderDiv.removeClass("admin-success-blink");
                        }, 500);

                        this._adminCatalogPrefixLabel.removeClass("hide");
                        this._adminCatalogPostfixLabel.removeClass("hide");
                        this._adminTimePicker.removeClass("hide");
                        this._adminCatalogComboBox.removeClass("hide");
                        this._adminActivateCatalogButton.removeClass("hide");
                        this._adminBulkOrderDiv.removeClass("admin-loading");
                    }/*,
                    errorResponse => {
                        this._errorResponse = errorResponse.responseJSON;
                        this._adminTimePickerLabel.text(this._errorResponse.message);

                        this._adminTimePicker.addClass("core-inputField-error");
                        this._adminActivateCatalogButton.addClass("admin-submitButton-disabled").removeClass("admin-submitButton-enabled");
                        this._adminTimePickerLabel.show();
                        this._adminBulkOrderDiv.addClass("admin-failure-blink");
                        setTimeout(() =>
                        {
                            this._adminBulkOrderDiv.removeClass("admin-failure-blink");
                        }, 500);

                        this._adminCatalogPrefixLabel.removeClass("hide");
                        this._adminCatalogPostfixLabel.removeClass("hide");
                        this._adminTimePicker.removeClass("hide");
                        this._adminCatalogComboBox.removeClass("hide");
                        this._adminActivateCatalogButton.removeClass("hide");
                        this._adminBulkOrderDiv.removeClass("admin-loading");
                    }*/
                );
            }
        }

        private getProductCatalogs(): void {
            AdminService.loadProductCatalogs(availableProductCatalogs => {
                this._availableProductCatalogs = availableProductCatalogs;
                for(let i = 0; i < this._availableProductCatalogs.length; i++) {
                    this._adminCatalogComboBox.append( new Option(this._availableProductCatalogs[i].name,i.toString()));
                }
                console.log(this._adminCatalogComboBox.children('option').length);
                if(this._adminCatalogComboBox.children('option').length>0){
                    this._adminCatalogComboBox.show();
                    this._adminCatalogPrefixLabel.text("Warenkorb für");
                    this._adminCatalogPostfixLabel.text("aktivieren bis");
                    this._adminTimePicker.show();
                    this._adminTimePicker.prop('readonly', false);
                    this._adminTimePicker.on("change", () => this.validateTime());
                }
            });
        }

        private uploadProductCatalog(): void {

            /* Workaround: (this._adminFileInput[0] as any).files[0] instead of this._adminFileInput[0].files[0] because
               Typescript doesn't know the property "files" at HTMLElements
               -> Update of Typescript should fix this
            */
            let file = (this._adminFileInput[0] as any).files[0];

            let formData = new FormData();
            formData.append('file',file);

            this._adminUploadCatalogButton.addClass("hide");
            this._adminCatalogDiv.addClass("admin-loading");


            AdminService.uploadProductCatalog(formData,onSuccess => {
                this._adminUploadCatalogButton.removeClass("hide");
                this._adminCatalogDiv.removeClass("admin-loading");
                this._adminCatalogDiv.addClass("admin-success-blink");
                setTimeout(() =>
                {
                    this._adminCatalogDiv.removeClass("admin-success-blink");
                    /*this.resetGUI();
                    this.getProductCatalogs();
                    this.getCurrentBulkOrder();*/
                }, 500);
            },onError => {
                this._adminUploadCatalogButton.removeClass("hide");
                this._adminCatalogDiv.removeClass("admin-loading");
            });


           /* $.ajax({
                type: "POST",
                url: WebService.adminProductCatalog + "/upload",
                scriptCharset: "utf-8",
                contentType: false,
                processData: false,
                cache: false,
                data: formData,
                async: true,
                success: function(result){console.log("success")},
                error: (xhr: JQueryXHR) => { new ShowErrorDialog(xhr) },
                xhr: function() {
                    var xhr = $.ajaxSettings.xhr();
                    if(xhr.upload){
                        console.log("pgorgress4");
                        xhr.upload.addEventListener("progress",function(event) {
                            console.log("pgorgress3");
                            if (event.lengthComputable) {
                                let percentComplete = event.loaded / event.total;
                                let ausgabe = Math.ceil((event.loaded / event.total) * 100) + '%';
                                console.log(percentComplete + "#" + ausgabe);
                                $('#_progress').width(Math.ceil((event.loaded / event.total) * 100) + '%');
                            }
                        },false);
                    return xhr;
                    }
                }
            });*/
        }

        /*private uploadProgressEvent(event: any): void
        {

        }*/

        private getCurrentBulkOrder(): void
        {
            AdminService.loadBulkOrders(bulkOrder =>
            {
                this._bulkOrder = bulkOrder;
                let currentTime = new Date().getTime();
                for(let i = 0; i < this._bulkOrder.length; i++)
                    if((this._bulkOrder[i].activeUntil - currentTime) > 0)
                        this._currentBulkOrder = this._bulkOrder[i];

                if(this._currentBulkOrder){

                    this._adminCardButton.removeClass("admin-button-disabled").addClass("admin-button-enabled");
                    this._adminCardButton.off();
                    //this._adminCardButton.on("click",function(){window.location.replace('/admin/ordermanagement')});

                    this._adminCatalogComboBox.hide();
                    this._adminCatalogPostfixLabel.hide();

                    this._adminCatalogPrefixLabel.text("Warenkorb \""+this._currentBulkOrder.name+"\" ist noch aktiv bis");
                    this._adminTimePicker.show();
                    this._adminTimePicker.prop('readonly',true);
                    console.log(new Date(this._currentBulkOrder.activeUntil).toString());
                    this._adminTimePicker.val(new Date(this._currentBulkOrder.activeUntil).toTimeString().substr(0,5));
                    this._adminTimePicker.off();

                    this._adminActivateCatalogButton.off();
                    this._adminActivateCatalogButton.text("Abbrechen");
                    this._adminActivateCatalogButton.on("click", () => this.deaktivateBulkOrder());
                }
            });
        }
    }




}





