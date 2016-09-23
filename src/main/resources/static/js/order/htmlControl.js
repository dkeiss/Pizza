var HtmlControl = (function () {
    function HtmlControl(productCategory, categoryElement, productElement) {
        this._category = productCategory;
        this._categoryElement = categoryElement;
        this._productElement = productElement;
        this.createButtonMenu();
        this.showProductList();
    }
    // Die Buttons für die einzelnen Kategorien werden erstellt
    HtmlControl.prototype.createButtonMenu = function () {
        var output = "";
        for (var i = 0; i < this._category.length; i++) {
            var categoryName = this._category[i].name;
            output += "<div class=\"buttonCategory\" onclick=\"htmlControl.setVisible(this, 'category-" + categoryName + "')\"><i class=\"fa fa-angle-double-right fa-1\" aria-hidden=\"true\"></i>" + categoryName + "</div>";
        }
        this._categoryElement.innerHTML = output;
    };
    // Alle Produkte werden aufgebaut
    HtmlControl.prototype.showProductList = function () {
        var output = "";
        for (var i = 0; i < this._category.length; i++) {
            var categoryName = this._category[i].name;
            output += "<div class=\"productCategory\" id=\"category-" + categoryName + "\">";
            var subCategory = this._category[i].subCategories;
            for (var j = 0; j < subCategory.length; j++) {
                if (subCategory[j].products.length !== 0) {
                    output += this.createProductTable(subCategory[j]);
                }
            }
            output += "</div>";
        }
        this._productElement.innerHTML = output;
    };
    HtmlControl.prototype.createProductTable = function (subCategory) {
        var output = "<table class=\"subCategory\"><tbody>";
        output += "<tr class=\"subCategory-header\"><th><h3>" + subCategory.name + "</h3></th>";
        var showProductSize = [false, false, false, false];
        for (var _i = 0, _a = subCategory.products; _i < _a.length; _i++) {
            var productItem = _a[_i];
            if (productItem.priceSmall !== "") {
                showProductSize[0] = true;
            }
            if (productItem.priceMedium !== "") {
                showProductSize[1] = true;
            }
            if (productItem.priceLarge !== "") {
                showProductSize[2] = true;
            }
            if (productItem.Price !== "") {
                showProductSize[3] = true;
            }
        }
        var changeSize = false;
        if (showProductSize[0] && showProductSize[3]) {
            showProductSize[3] = false;
            changeSize = true;
        }
        if (showProductSize[0]) {
            output += "<th class=\"size\">Klein</th>";
        }
        if (showProductSize[1]) {
            output += "<th class=\"size\">Mittel</th>";
        }
        if (showProductSize[2]) {
            output += "<th class=\"size\">Gro\u00DF</th>";
        }
        if (showProductSize[3]) {
            output += "<th class=\"size\"></th>";
        }
        output += "</tr>";
        var products = subCategory.products;
        for (var _b = 0; _b < products.length; _b++) {
            var product = products[_b];
            output += "<tr class=\"subCategory-products\">";
            output += "<td class=\"name\"><span>" + product.number + ".</span> " + product.name + " <br />";
            output += "<span class=\"description\">" + product.description + "</span></td>";
            if (product.price !== "" && !changeSize) {
                output += this.createOrderButtonForTableCell(product, product.price, "normal");
            }
            else {
                if (product.priceSmall !== "") {
                    output += this.createOrderButtonForTableCell(product, product.priceSmall, "klein");
                }
                if (product.priceSmall === "" && changeSize) {
                    output += this.createOrderButtonForTableCell(product, product.price, "klein");
                }
                // Eine leere Zelle, damit die mittlere Zelle richtig positioniert wird...
                if (product.priceSmall === "" && showProductSize[0]) {
                    output += "<td></td>";
                }
                if (product.priceMedium !== "") {
                    output += this.createOrderButtonForTableCell(product, product.priceMedium, "mittel");
                }
                if (product.priceLarge !== "") {
                    output += this.createOrderButtonForTableCell(product, product.priceLarge, "groß");
                }
            }
            output += "</tr>";
        }
        return output + "</tbody></table>";
    };
    HtmlControl.prototype.createOrderButtonForTableCell = function (product, price, size) {
        return "<td class=\"price\"><div category=\"" + product.category + "\" name=\"" + product.name + "\" number=\"" + product.number + "\" price=\"" + price + "\" size=\"" + size + "\" onclick=\"orderControl.loadExtraMenu(this)\">" + price + " \u20AC</div></td>";
    };
    HtmlControl.prototype.setVisible = function (buttonElement, buttonName) {
        var categoryVisible = document.getElementsByClassName("category-visible");
        for (var i = 0; i < categoryVisible.length; i++) {
            categoryVisible[i].classList.remove("category-visible");
        }
        document.getElementById(buttonName).classList.add("category-visible");
        document.getElementById("rest").classList.remove("invisible");
        var buttonCategory = document.getElementsByClassName("buttonCategory");
        for (var i = 0; i < buttonCategory.length; i++) {
            buttonCategory[i].classList.remove("buttonCategory-active");
        }
        buttonElement.classList.add("buttonCategory-active");
    };
    return HtmlControl;
})();
//# sourceMappingURL=htmlControl.js.map