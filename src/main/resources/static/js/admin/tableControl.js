/// <reference path="../../WebService/js/service.ts" />
var TableControl = (function () {
    function TableControl(tData) {
        var output = "<tr><th>Benutzer</th><th>Id</th><th>Name</th><th>Extra Item</th><th>Größe</th><th>Preis</th><th>Bezahlt?</th><th>Löschen</th></tr>";
        for (var i = 0; i < tData.length; i++) {
            output += "<tr>";
            output += "<td>" + tData[i].UserName + "</td>";
            output += "<td>" + tData[i].Id + "</td>";
            output += "<td>" + tData[i].Name + "</td>";
            output += "<td>" + tData[i].ExtraItem + "</td>";
            output += "<td>" + tData[i].Size + "</td>";
            output += "<td>" + tData[i].PriceWithDiscount.toFixed(2) + " \u20AC</td>";
            if (tData[i].Paid === "[yes]") {
                //output += `<td><input type="checkbox" name="${tData[i].UserName}" time="${tData[i].Time}" onclick="tableControl.clickPayed(this)" checked /></td>`;
                output += "<td><img src=\"../image/tick_green.png\" /></td>";
            }
            else {
                //output += `<td><input type="checkbox" name="${tData[i].UserName}" time="${tData[i].Time}" onclick="tableControl.clickPayed(this)" /></td>`;
                output += "<td><img src=\"../image/add_blue.png\" /></td>";
            }
            output += "<td><img src=\"../image/close_red.png\" /></td>";
            output += "</tr>";
        }
        document.getElementById("table-input").innerHTML = output;
    }
    TableControl.prototype.clickPayed = function (element) {
        var userName = element.getAttribute("name");
        var time = element.getAttribute("time");
        var payed = Service.changePayedStatus(userName, time);
        if (payed) {
            element.checked = true;
        }
        else {
            element.checked = false;
        }
    };
    return TableControl;
})();
//# sourceMappingURL=tableControl.js.map