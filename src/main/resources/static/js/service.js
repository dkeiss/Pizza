var Service = (function () {
    function Service() {
    }
    /* ############  WebAdmin  ############ */
    Service.generateJson = function () {
        var value = this.ajax("GenerateJsonFromWebsite");
        if (value) {
            document.getElementById("button_generateJson").style.backgroundColor = "#007f00";
        }
        else {
            document.getElementById("button_generateJson").style.backgroundColor = "#FF0000";
        }
    };
    Service.addNewUser = function (newUser) {
        return this.ajax("AddNewUser", "{newUser: '" + newUser + "'}");
    };
    Service.loadUserTable = function () {
        return this.ajax("user");
    };
    /* ############  WebAdmin/Warenkorb  ############ */
    Service.loadTableData = function () {
        return this.ajax("GetUserOrder");
    };
    Service.changePayedStatus = function (userName, time) {
        return this.ajax("IsPayed", "{userName: '" + userName + "', time: '" + time + "'}");
    };
    Service.removeUserOrder = function (userName, time, discount) {
        return this.ajax("RemoveUserOrder", "{userName: '" + userName + "', time: '" + time + "', discount: '" + discount + "'}");
    };
    /* ############  WebOrder  ############ */
    Service.loadFoodMenu = function () {
        var productData = this.ajax("ResponseFoodMenu");
        return productData.ProductCategories;
    };
    Service.loadAdditionalMenu = function (productId) {
        return this.ajax("GetAdditionalInfo", "{value: '" + productId + "'}");
    };
    Service.checkUserDiscount = function (userName) {
        return this.ajax("GetUserDiscount", "{userName: '" + userName + "'}");
    };
    Service.orderProduct = function (userName, itemCategory, itemId, itemName, itemExtra, itemSize, itemPriceWithDiscount, itemPriceWithoutDiscount) {
        var jdata = "{userName: '" + userName + "', itemCategory: '" + itemCategory + "', itemId: " + itemId + ", itemName: '" + itemName + "', itemExtra: '" + itemExtra + "', itemSize: '" + itemSize + "', itemPriceWithDiscount: " + itemPriceWithDiscount + ", itemPriceWithoutDiscount: " + itemPriceWithoutDiscount + "}";
        return this.ajax("SaveUserOrder", jdata);
    };
    /* ############  WebService  ############ */
    Service.ajax = function (serviceMethod, data) {
        if (data === void 0) { data = null; }
        var respons = $.ajax({
            type: "POST",
            url: this.webserviceUlr + serviceMethod,
            data: data,
            scriptCharset: "utf-8",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            async: false
        });
        respons.fail(function (data) {
            alert("Fail: " + JSON.parse(data.d));
        });
        var serviceData = null;
        respons.done(function (data) {
            serviceData = data.d;
        });
        return serviceData;
    };
    //private static webserviceUlr = "../../WebService/Service.asmx/";
    Service.webserviceUlr = window.location.origin + "/";
    return Service;
})();
//# sourceMappingURL=service.js.map