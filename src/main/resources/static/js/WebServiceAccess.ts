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
}