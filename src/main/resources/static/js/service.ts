/// <reference path="jquery.d.ts" />

class Service
{
    //private static webserviceUlr = "../../WebService/Service.asmx/";
    private static webserviceUlr = `${window.location.origin}/rest/`;

    /* ############  WebAdmin  ############ */
    public static generateJson()
    {
        const value = this.ajax("GenerateJsonFromWebsite");

        if (value) {
            document.getElementById("button_generateJson").style.backgroundColor = "#007f00";
        }
        else {
            document.getElementById("button_generateJson").style.backgroundColor = "#FF0000";
        }
    }

    public static addNewUser(newUser: string): boolean
    {
        return this.ajax("AddNewUser", `{newUser: '${newUser}'}`);
    }

    public static loadUserTable(): any
    {
        return this.ajaxGet("users");
    }

    /* ############  WebAdmin/Warenkorb  ############ */
    public static loadTableData(): any
    {
        return this.ajax("GetUserOrder");
    }

    public static changePayedStatus(userName: string, time: string): any
    {
        return this.ajax("IsPayed", `{userName: '${userName}', time: '${time}'}`);
    }

    public static removeUserOrder(userName: string, time: string, discount: number): any
    {
        return this.ajax("RemoveUserOrder", `{userName: '${userName}', time: '${time}', discount: '${discount}'}`);
    }

    /* ############  WebOrder  ############ */
    public static loadFoodMenu(): any
    {
        const productData = this.ajax("ResponseFoodMenu");
        return productData.ProductCategories;
    }

    public static loadAdditionalMenu(productId: number): any
    {
        return this.ajax("GetAdditionalInfo", `{value: '${productId}'}`);
    }

    public static checkUserDiscount(userName: string): number
    {
        return this.ajax("GetUserDiscount", `{userName: '${userName}'}`);
    }

    public static orderProduct(userName: string, itemCategory: string, itemId: number, itemName: string, itemExtra: string, itemSize: string, itemPriceWithDiscount: number, itemPriceWithoutDiscount: number): boolean
    {
        const jdata = `{userName: '${userName}', itemCategory: '${itemCategory}', itemId: ${itemId}, itemName: '${itemName}', itemExtra: '${itemExtra}', itemSize: '${itemSize}', itemPriceWithDiscount: ${itemPriceWithDiscount}, itemPriceWithoutDiscount: ${itemPriceWithoutDiscount}}`;
        return this.ajax("SaveUserOrder", jdata);
    }


    /* ############  WebService  ############ */
    private static ajax(serviceMethod:string, data:any = null): any
    {
        const respons = $.ajax({
            type: "POST",
            url: this.webserviceUlr + serviceMethod,
            data: data,
            scriptCharset : "utf-8",
            contentType : "application/json; charset=utf-8",
            dataType : "json",
            async : false
        });

        respons.fail((data) => {
            alert(`Fail: ${JSON.parse(data.d)}`);
        });

        var serviceData = null;
        respons.done((data) =>
        {
            serviceData =  data.d;
        });

        return serviceData;
    }
    private static ajaxGet(serviceMethod:string, data:any = null): any
    {
        const respons = $.ajax({
            type: "GET",
            url: this.webserviceUlr + serviceMethod,
            data: data,
            scriptCharset : "utf-8",
            contentType : "application/json; charset=utf-8",
            dataType : "json",
            async : false
        });

        respons.fail((data) => {
            alert(`Fail: ${JSON.parse(data.d)}`);
        });

        var serviceData = null;
        respons.done((data) =>
        {
            serviceData =  data.d;
        });

        return serviceData;
    }
}