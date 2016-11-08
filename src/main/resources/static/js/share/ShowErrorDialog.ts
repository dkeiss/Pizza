/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="Constants.ts" />

class ShowErrorDialog
{
    private _container: JQuery = null;
    private _boxCenter: JQuery = null;
    private _box: JQuery = null;
    private _title: JQuery = null;
    private _text: JQuery = null;
    private _refreshButton: JQuery = null;

    private _cssShowContainer = "core-serverError-showContainer";
    private _cssShowErrorButton = "core-serverError-showButton";


    constructor(xhr?: JQueryXHR, title?: string, text?:string)
    {
        this._boxCenter = $(ErrorDialogSelectors.boxCenter);
        this._box = $(ErrorDialogSelectors.box);
        this._title = $(ErrorDialogSelectors.title);
        this._text = $(ErrorDialogSelectors.text);

        if (xhr)
        {
            this.xhrError(xhr);
        }
        else if (title || title && text)
        {
            this.userError(title, text);
        }
        else
        {
            this.userError("Unbekannter Fehler");
        }

        this._container = $(ErrorDialogSelectors.container).addClass(this._cssShowContainer);
        this._refreshButton = $(ErrorDialogSelectors.refreshButton).addClass(this._cssShowErrorButton).on("click", () => location.reload());

        this._boxCenter.each((index, element) => {
            $(element).width(this._box.width() + 10);
        });
    }

    private xhrError(xhr: JQueryXHR)
    {
        if (xhr.status == 404)
        {
            this.showServerDialogWithoutDetails();
        }
        else
        {
            this.showServerDialogWithDetails(xhr);
        }
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

    private userError(title: string, text?: string)
    {
        this._title.text(title);
        if (text) this._text.text(text);
    }
}