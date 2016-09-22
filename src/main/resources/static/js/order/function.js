/// <reference path="../../WebService/js/jquery.d.ts" />
/// <reference path="../../WebService/js/service.ts" />
/// <reference path="htmlControl.ts"/>
/// <reference path="orderControl.ts"/>
var htmlControl;
var orderControl;
$(document).ready(function () {
    document.getElementById("ajax-load").style.display = "block";
    var productCategory = Service.loadFoodMenu();
    var categoryElement = document.getElementById("categorySelector");
    var productElement = document.getElementById("productList");
    htmlControl = new HtmlControl(productCategory, categoryElement, productElement);
    orderControl = new OrderControl();
    document.getElementById("ajax-load").style.display = "none";
});
//# sourceMappingURL=function.js.map