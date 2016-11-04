/// <reference path="thirdParty/jquery.d.ts" />

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
            error: (xhr: JQueryXHR) =>
            {
                alert(xhr.statusText + " - " + xhr.status);
            }
        });
    }

    public static ajaxPost(webServiceMethod: string, postData: any, onSuccess: (xhr: any) => void, onError: (xhr: any) => void)
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
            error: onError
        });
    }

    public static ajaxPut(webServiceMethod: string, putData: any)
    {
        $.ajax({
            type: "PUT",
            url: webServiceMethod,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(putData),
            async: false,
            error: (xhr: JQueryXHR) =>
            {
                alert(xhr.statusText + " - " + xhr.status);
                console.log(xhr);
            },
            success: (xhr: JQueryXHR) =>
            {
                alert(xhr.statusText + " - " + xhr.status);
                console.log(xhr);
            },
        });
    }

    public static ajaxDelete(webServiceMethod: string, deleteData: any, onSuccess: (xhr: any) => void)
    {
        $.ajax({
            type: "DELETE",
            url: webServiceMethod + '/' +  deleteData,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false,
            success: onSuccess,
            error: (xhr: JQueryXHR) =>
            {
                alert(xhr.statusText + " - " + xhr.status);
            }
        });
    }
}