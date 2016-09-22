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
            var categoryName = this._category[i].Name;
            output += "<div class=\"buttonCategory\" onclick=\"htmlControl.setVisible(this, 'category-" + categoryName + "')\"><i class=\"fa fa-angle-double-right fa-1\" aria-hidden=\"true\"></i>" + categoryName + "</div>";
        }
        this._categoryElement.innerHTML = output;
    };
    // Alle Produkte werden aufgebaut
    HtmlControl.prototype.showProductList = function () {
        var output = "";
        for (var i = 0; i < this._category.length; i++) {
            var categoryName = this._category[i].Name;
            output += "<div class=\"productCategory\" id=\"category-" + categoryName + "\">";
            var subCategory = this._category[i].SubCategories;
            for (var j = 0; j < subCategory.length; j++) {
                if (subCategory[j].Products.length !== 0) {
                    output += this.createProductTable(subCategory[j]);
                }
            }
            output += "</div>";
        }
        this._productElement.innerHTML = output;
    };
    HtmlControl.prototype.createProductTable = function (subCategory) {
        var output = "<table class=\"subCategory\"><tbody>";
        output += "<tr class=\"subCategory-header\"><th><h3>" + subCategory.Name + "</h3></th>";
        var showProductSize = [false, false, false, false];
        for (var _i = 0, _a = subCategory.Products; _i < _a.length; _i++) {
            var productItem = _a[_i];
            if (productItem.PriceSmall !== "") {
                showProductSize[0] = true;
            }
            if (productItem.PriceMedium !== "") {
                showProductSize[1] = true;
            }
            if (productItem.PriceLarge !== "") {
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
        var products = subCategory.Products;
        for (var _b = 0; _b < products.length; _b++) {
            var product = products[_b];
            output += "<tr class=\"subCategory-products\">";
            output += "<td class=\"name\"><span>" + product.Number + ".</span> " + product.Name + " <br />";
            output += "<span class=\"description\">" + product.Description + "</span></td>";
            if (product.Price !== "" && !changeSize) {
                output += this.createOrderButtonForTableCell(product, product.Price, "normal");
            }
            else {
                if (product.PriceSmall !== "") {
                    output += this.createOrderButtonForTableCell(product, product.PriceSmall, "klein");
                }
                if (product.PriceSmall === "" && changeSize) {
                    output += this.createOrderButtonForTableCell(product, product.Price, "klein");
                }
                // Eine leere Zelle, damit die mittlere Zelle richtig positioniert wird...
                if (product.PriceSmall === "" && showProductSize[0]) {
                    output += "<td></td>";
                }
                if (product.PriceMedium !== "") {
                    output += this.createOrderButtonForTableCell(product, product.PriceMedium, "mittel");
                }
                if (product.PriceLarge !== "") {
                    output += this.createOrderButtonForTableCell(product, product.PriceLarge, "groß");
                }
            }
            output += "</tr>";
        }
        return output + "</tbody></table>";
    };
    HtmlControl.prototype.createOrderButtonForTableCell = function (product, price, size) {
        return "<td class=\"price\"><div category=\"" + product.Category + "\" name=\"" + product.Name + "\" number=\"" + product.Number + "\" price=\"" + price + "\" size=\"" + size + "\" onclick=\"orderControl.loadExtraMenu(this)\">" + price + " \u20AC</div></td>";
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