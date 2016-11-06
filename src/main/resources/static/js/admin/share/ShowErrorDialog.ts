/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="Constants.ts" />

class ShowErrorDialog
{
    private _container: JQuery = null;
    private _title: JQuery = null;
    private _text: JQuery = null;
    private _refreshButton: JQuery = null;

    private _cssShowContainer = "core-serverError-showContainer";
    private _cssShowErrorButton = "core-serverError-showButton";


    constructor(xhr: JQueryXHR)
    {
        this._title = $(ErrorDialogSelectors.title);
        this._text = $(ErrorDialogSelectors.text);

        if (xhr.status == 404)
        {
            this.showServerDialogWithoutDetails();
        }
        else
        {
            this.showServerDialogWithDetails(xhr);
        }

        this._container = $(ErrorDialogSelectors.container).addClass(this._cssShowContainer);
        this._refreshButton = $(ErrorDialogSelectors.refreshButton).addClass(this._cssShowErrorButton).on("click", () => location.reload());
    }

    private showServerDialogWithoutDetails(): void
    {
        this._title.text("Cannot connect to server");
    }

    private showServerDialogWithDetails(xhr: JQueryXHR): void
    {
        this._title.text(xhr.statusText);
        this._text.html(ShowErrorDialog.createErrorResponse(xhr));
    }

    private static createErrorResponse(xhr: JQueryXHR): string
    {
        return  "<span>Error:</span>   " + xhr.responseJSON.error + "<br />" +
                "<span>Status:</span>   " + xhr.responseJSON.status + "<br />" +
                "<span>Message:</span>   " + xhr.responseJSON.message;
    }
}