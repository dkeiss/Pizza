var CreateHtml = (function () {
    function CreateHtml() {
    }
    CreateHtml.prototype.CreateHtml = function () {
    };
    CreateHtml.Menu = function () {
        var output = "";
        var productCategories = data.productCategories;
        for (var categoryIndex = 0; categoryIndex < productCategories.length; categoryIndex++) {
            var productCategory = productCategories[categoryIndex];
            var categoryName = productCategory.name;
            output += "<div onclick=\"application.setVisible('" + this.getNameAsId(categoryName) + "')\" class=\"categoryButton\" id=\"" + this.getNameAsId(categoryName) + "button\">" + this.getEncodedHtml(categoryName) + "</div>";
        } //die Buttons für die einzelnen Kategorien werden erstellt
        this._categorySelectorElement.innerHTML = output;
    };
    return CreateHtml;
})();
//# sourceMappingURL=CreateHtml.js.map