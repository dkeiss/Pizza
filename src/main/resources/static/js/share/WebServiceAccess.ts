/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="ShowErrorDialog.ts" />

class WebServiceAccess
{
    public static ajaxGet(webServiceMethod: string, onSuccess: (xhr: any) => void)
    {
        $.ajax({
            type: "GET",
            url: webServiceMethod,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: onSuccess,
            error: (xhr: JQueryXHR) => { new ShowErrorDialog(xhr) }
        });
    }

    public static ajaxPost(webServiceMethod: string, postData: any, onSuccess: (xhr: any) => void, onError?: (xhr: any) => void)
    {
        $.ajax({
            type: "POST",
            url: webServiceMethod,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(postData),
            async: false,
            success: onSuccess,
            error: (xhr: JQueryXHR) => { new ShowErrorDialog(xhr) }
        });
    }

    public static ajaxPut(webServiceMethod: string, putData: any, onSuccess: (xhr: IOnSuccess) => void)
    {
        $.ajax({
            type: "PUT",
            url: webServiceMethod,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(putData),
            async: false,
            success: onSuccess,
            error: (xhr: JQueryXHR) => { new ShowErrorDialog(xhr) }
        });
    }

    public static ajaxDelete(webServiceMethod: string, deleteData: any, onSuccess: (xhr: IOnSuccess) => void)
    {
        $.ajax({
            type: "DELETE",
            url: webServiceMethod + '/' +  deleteData,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: onSuccess,
            error: (xhr: JQueryXHR) => { new ShowErrorDialog(xhr) }
        });
    }

    public static ajaxPostMultipart(webServiceMethod: string, postData: any, onSuccess: (xhr: any) => void, onError?: (xhr: any) => void)
    {
        $.ajax({
            type: "POST",
            url: webServiceMethod,
            scriptCharset: "utf-8",
            contentType: false,
            processData: false,
            cache: false,
            data: postData,
            async: false,
            success: onSuccess,
            error: (xhr: JQueryXHR) => { new ShowErrorDialog(xhr) }
        });
    }
}




interface IOnSuccess
{
    success: boolean;
}