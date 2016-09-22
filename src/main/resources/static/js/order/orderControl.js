/// <reference path="../../WebService/js/service.ts" />
var OrderControl = (function () {
    function OrderControl() {
        this._strLabel = "Wählen Sie hier ihre Extrabeilagen oder Saucen aus.";
        this._itemCategory = "";
        this._itemId = 0;
        this._itemName = "";
        this._itemPrice = 0.0;
        this._itemSize = "";
        this._userNameFound = "";
        this._userDiscount = 0;
        this._ingredientsPrice = 0.0;
    }
    OrderControl.prototype.loadExtraMenu = function (element) {
        // Daten laden / reseten
        document.getElementById("productItem").innerHTML = " " + element.getAttribute("number") + ".  " + element.getAttribute("name");
        document.getElementById("productPrice").innerHTML = element.getAttribute("price") + " \u20AC";
        document.getElementById("menu-extra-calc").innerHTML = "0.00 \u20AC";
        this.showOrderButtonAccepted(false);
        this._itemCategory = element.getAttribute("category");
        this._itemId = Number(element.getAttribute("number"));
        this._itemName = element.getAttribute("name");
        this._itemPrice = Number(element.getAttribute("price"));
        this._itemSize = element.getAttribute("size");
        this._ingredientsPrice = 0.0;
        this.calcOrder();
        // Extra Zutaten werden vom Server geholt & angezeigt
        var productId = Number(element.getAttribute("number"));
        var jData = Service.loadAdditionalMenu(productId);
        var output = "";
        if (jData !== null) {
            // ReSharper disable once QualifiedExpressionMaybeNull
            for (var i = 0; i < jData.As.length; i++) {
                output = this.createAdditionalMenu(jData.As[i], element, output);
            }
        }
        document.getElementById("menu-extra").innerHTML = output;
        document.getElementById("container-order").classList.add("container-order-visible");
    };
    /// HTML-Konstruktor für Extra-Zutaten
    OrderControl.prototype.createAdditionalMenu = function (jDataA, element, output) {
        var duty = jDataA.Duty;
        output += "<div class=\"box boxExtraMenu\" duty=\"" + duty + "\">";
        output += "<label>" + this._strLabel + "</label>";
        output += "<div>";
        var size = element.getAttribute("size");
        for (var i = 0; i < jDataA.Bs.length; i++) {
            var tempSize = -1;
            if (size === "klein" || size === "normal") {
                tempSize = 0;
            }
            if (size === "mittel") {
                tempSize = 1;
            }
            if (size === "groß") {
                tempSize = 2;
            }
            var price = jDataA.Bs[i].Prices[tempSize];
            var name_1 = jDataA.Bs[i].Name;
            output += "<button class=\"button-ingredients\" onclick=\"orderControl.clickExtraIngredients(this)\" name=\"" + name_1 + "\" price=\"" + price.toFixed(2) + "\">";
            output += "<p>" + name_1 + "</p>";
            output += "<span>" + price.toFixed(2) + "</span>";
            output += "</button>";
        }
        output += "</div></div>";
        return output;
    };
    OrderControl.prototype.clickExtraIngredients = function (element) {
        var elementButtons = element.parentElement.getElementsByClassName("button-ingredients");
        var duty = this.findParentElementWithClass(element, "boxExtraMenu").getAttribute("duty");
        this.findParentElementWithClass(element, "boxExtraMenu").classList.remove("boxExtraMenu-error");
        var price = 0.0;
        if (duty === "true") {
            this.removeClassFromElements(elementButtons, "btActive");
            element.classList.add("btActive");
            price = Number(element.getAttribute("price"));
        }
        else {
            element.classList.toggle("btActive");
            var buttonActives = document.getElementById("menu-extra").getElementsByClassName("btActive");
            for (var i = 0; i < buttonActives.length; i++) {
                price = parseFloat((Number(buttonActives[i].getAttribute("price")) + price).toFixed(2));
            }
        }
        document.getElementById("menu-extra-calc").innerHTML = price.toFixed(2) + " \u20AC";
        this._ingredientsPrice = Number(price.toFixed(2));
        this.calcOrder();
    };
    OrderControl.prototype.findParentElementWithClass = function (element, className) {
        while ((element = element.parentElement) && !element.classList.contains(className))
            ;
        return element;
    };
    OrderControl.prototype.removeClassFromElements = function (elements, className) {
        for (var i = 0; i < elements.length; i++) {
            elements[i].classList.remove(className);
        }
    };
    OrderControl.prototype.checkUserDiscount = function (element) {
        var discount = Service.checkUserDiscount(element.value);
        if (discount !== -1) {
            element.style.background = "#67C100";
            this._userNameFound = element.value;
        }
        else {
            element.style.background = "";
            this._userNameFound = "";
            discount = 0;
        }
        document.getElementById("discount").innerHTML = "- " + discount.toFixed(2) + " \u20AC";
        this._userDiscount = discount;
        this.calcOrder();
    };
    // Überpürfen der Berstellung!, wenn alles in Ordnung ist, wird abgeschickt!
    OrderControl.prototype.orderAcceptButton = function (element) {
        if (0 === this._userNameFound.length) {
            document.getElementById("input-User").style.background = "#f1c9c9";
            return;
        }
        if (!this.checkExtraMenuSelected()) {
            return;
        }
        var strAdded = "";
        var buttonAccepted = document.getElementsByClassName("btActive");
        for (var i = 0; i < buttonAccepted.length; i++) {
            strAdded += buttonAccepted[i].getAttribute("name") + ", ";
        }
        strAdded = strAdded.substring(0, strAdded.lastIndexOf(","));
        var priceWithoutDiscount = Number(this._itemPrice + this._ingredientsPrice);
        var priceWithDiscount = Number(this._itemPrice + this._ingredientsPrice - this._userDiscount);
        if (0 > priceWithDiscount)
            priceWithDiscount = 0.0;
        var orderSuccess = Service.orderProduct(this._userNameFound, this._itemCategory, this._itemId, this._itemName, strAdded, this._itemSize, priceWithDiscount, priceWithoutDiscount);
        if (orderSuccess) {
            this.showOrderButtonAccepted(true);
            this._userDiscount = 0;
            document.getElementById("discount").innerHTML = "- " + this._userDiscount.toFixed(2) + " \u20AC";
        }
    };
    OrderControl.prototype.checkExtraMenuSelected = function () {
        var extraMenus = document.getElementsByClassName("boxExtraMenu");
        var checked = true;
        for (var i = 0; i < extraMenus.length; i++) {
            if (extraMenus[i].getAttribute("duty") === "true") {
                var btActives = extraMenus[i].getElementsByClassName("btActive");
                if (btActives.length === 0) {
                    extraMenus[i].classList.add("boxExtraMenu-error");
                    checked = false;
                }
            }
        }
        return checked;
    };
    OrderControl.prototype.calcOrder = function () {
        var price = 0;
        price += this._itemPrice;
        price += this._ingredientsPrice;
        price -= this._userDiscount;
        price = Number(price.toFixed(2));
        if (0 > price)
            price = 0;
        document.getElementById("order-click-button").innerHTML = "Bestellen " + price.toFixed(2) + " \u20AC";
        document.getElementById("order-click-button-accepted").innerHTML = "Bestellung akzeptiert: " + price.toFixed(2) + " \u20AC";
    };
    OrderControl.prototype.closeExtraMenu = function () {
        document.getElementById("container-order").classList.remove("container-order-visible");
    };
    OrderControl.prototype.showOrderButtonAccepted = function (value) {
        if (value) {
            document.getElementById("order-click-button").style.display = "none";
            document.getElementById("order-click-button-accepted").style.display = "block";
        }
        else {
            document.getElementById("order-click-button").style.display = "block";
            document.getElementById("order-click-button-accepted").style.display = "none";
        }
    };
    return OrderControl;
})();
//# sourceMappingURL=orderControl.js.map