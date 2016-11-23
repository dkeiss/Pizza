/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../share/Constants.ts" />
/// <reference path="IUserOrder.ts" />
/// <reference path="ShoppingCardService.ts" />

namespace WebApplication.Admin.ShoppingCard
{
    export class TableController {
        private _orderTable: JQuery = null;
        private _orderTableBody: JQuery = null;
        private _orderTableSortButton: JQuery = null;
        private _orderTableStateButton: JQuery = null;
        private _orderTableDeleteButton: JQuery = null;

        private _cardOrderSummaryDiv: JQuery = null;

        private _currentSortColumn: number;

        private _tableData: any;

        private _orderList: IUserOrder[] = null;

        private _orderSummary: {[productCategory : string]: number} = {};

        constructor()
        {
            this._currentSortColumn = -1;
            this._orderTable = $(ShoppingCardSelectors.orderTable);
            this._orderTableBody = this._orderTable.find("tbody");
            this._orderTableSortButton = $(ShoppingCardSelectors.orderTableSortButton);
            this._cardOrderSummaryDiv = $(ShoppingCardSelectors.cardOrderSummaryDiv);

            for(let i = 0; i <this._orderTableSortButton.length; i++) {
                $(this._orderTableSortButton[i]).on("click", () => this.sort(i));
            }
            this.getUserOrders();
            this.createTable();
        }

        private getUserOrders() : void {
            ShoppingCardService.loadUserOrders(orderList =>
            {
                this._orderList = orderList;
                /*this._orderList = [
                    {
                        userOrderId: 1,
                        userId: 2,
                        productId: 1,
                        productVariationId: 1,
                        number: 421,
                        userOrderAdditionals: [
                            {
                                userOrderAdditionalId: 1,
                                userOrderId: 1,
                                additionalId: 2,
                                additionalDescription: "test",
                                additionalPriceId: 1,
                                additionalPriceName: "test"

                            }],
                        firstName: "Sascha",
                        lastName: "Walther",
                        userName: "a@a.de",
                        productCategoryName: "Pizza",
                        productGroupName: "Pizza,",
                        productName: "Pizza Salami",
                        productVariationName: "Groß",
                        sum: 7.99,
                        paid: true
                    },
                    {
                        userOrderId: 2,
                        userId: 2,
                        productId: 1,
                        productVariationId: 1,
                        number: 572,
                        userOrderAdditionals: [
                            {
                                userOrderAdditionalId: 1,
                                userOrderId: 1,
                                additionalId: 2,
                                additionalDescription: "test",
                                additionalPriceId: 1,
                                additionalPriceName: "test"

                            }],
                        firstName: "Sascha",
                        lastName: "Walther",
                        userName: "a@a.de",
                        productCategoryName: "Pizza",
                        productGroupName: "Pizza,",
                        productName: "Pizza Salami",
                        productVariationName: "Groß",
                        sum: 5.55,
                        paid: true
                    },
                    {
                        userOrderId: 3,
                        userId: 2,
                        productId: 1,
                        productVariationId: 1,
                        number: 421,
                        userOrderAdditionals: [
                            {
                                userOrderAdditionalId: 1,
                                userOrderId: 1,
                                additionalId: 2,
                                additionalDescription: "test",
                                additionalPriceId: 1,
                                additionalPriceName: "test"

                            }],
                        firstName: "Sascha",
                        lastName: "Walther",
                        userName: "a@a.de",
                        productCategoryName: "Pizza",
                        productGroupName: "Pizza,",
                        productName: "Pizza Salami",
                        productVariationName: "Groß",
                        sum: 7.00,
                        paid: false
                    },
                    {
                        userOrderId: 4,
                        userId: 2,
                        productId: 1,
                        productVariationId: 1,
                        number: 576,
                        userOrderAdditionals: [
                            {
                                userOrderAdditionalId: 1,
                                userOrderId: 1,
                                additionalId: 2,
                                additionalDescription: "test",
                                additionalPriceId: 1,
                                additionalPriceName: "test"

                            }],
                        firstName: "Sascha",
                        lastName: "Walther",
                        userName: "a@a.de",
                        productCategoryName: "Muschu",
                        productGroupName: "Pizza,",
                        productName: "Pizza Salami",
                        productVariationName: "Groß",
                        sum: 7.59,
                        paid: false
                    },
                    {
                        userOrderId: 5,
                        userId: 2,
                        productId: 1,
                        productVariationId: 1,
                        number: 426,
                        userOrderAdditionals: [
                            {
                                userOrderAdditionalId: 1,
                                userOrderId: 1,
                                additionalId: 2,
                                additionalDescription: "test",
                                additionalPriceId: 1,
                                additionalPriceName: "test"

                            }],
                        firstName: "Sascha",
                        lastName: "Walther",
                        userName: "a@a.de",
                        productCategoryName: "Pizza",
                        productGroupName: "Pizza,",
                        productName: "Pizza Salami",
                        productVariationName: "Groß",
                        sum: 8.46,
                        paid: true
                    }
                ]*/
            });
        }

        private setSummaryData() : void {
            this._cardOrderSummaryDiv.empty();
            let productCategory;
            for(productCategory in this._orderSummary){
                if(this._orderSummary[productCategory]>0) {
                    let element = "";
                    element += "<span>" + productCategory + ": " + this._orderSummary[productCategory] + "</span>";
                    this._cardOrderSummaryDiv.append(element);
                }
            }
            let sum = 0;
            for(let i = 0; i < this._tableData.length; i++)
                sum += this._tableData[i][5];
            let element = "<br/><span>Gesamtpreis: "+ sum.toFixed(2).toString().replace(".",",") + " €</span>";
            this._cardOrderSummaryDiv.append(element);
        }

        private createTable() : void {

            this._tableData = new Array(this._orderList.length);

            for(let i = 0; i < this._orderList.length; i++) {
                if(this._orderList[i].productCategoryName in this._orderSummary)
                    this._orderSummary[this._orderList[i].productCategoryName]++;
                else
                    this._orderSummary[this._orderList[i].productCategoryName] = 1;

                let element = "";
                element += "<tr orderid='" + this._orderList[i].userOrderId + "'>";
                element += "<td >" +  this._orderList[i].firstName + " " + this._orderList[i].lastName + "</td>";
                element += "<td >" +  this._orderList[i].number + "</td>";
                element += "<td >" +  this._orderList[i].productName + "</td>";
                let additionString = "";
                if(this._orderList[i].userOrderAdditionals) {
                    for (let a = 0; a < this._orderList[i].userOrderAdditionals.length; a++) {
                        additionString += this._orderList[i].userOrderAdditionals[a].additionalDescription + ", ";
                    }
                    additionString = additionString.substring(0,additionString.length - 2);
                }
                element += "<td >" +  additionString + "</td>";
                element += "<td >" +  this._orderList[i].productVariationName + "</td>";
                element += "<td >" +  this._orderList[i].sum.toFixed(2).toString().replace(".",",") + " €</td>";
                element += "<td class='"+  (this._orderList[i].paid ? "sc-orderTable-paidIcon" : "sc-orderTable-outstandingIcon") + " card-orderTable_stateButton'></td>";
                element += "<td class='sc-orderTable-trashIcon card-orderTable_deleteButton'></td>";
                element += "</tr>";
                this._orderTableBody.append(element);

                this._tableData[i] = new Array(9);
                this._tableData[i][0] =  this._orderList[i].firstName + " " + this._orderList[i].lastName;
                this._tableData[i][1] =  this._orderList[i].number;
                this._tableData[i][2] =  this._orderList[i].productName;
                this._tableData[i][3] =  additionString;
                this._tableData[i][4] =  this._orderList[i].productVariationName;
                this._tableData[i][5] =  this._orderList[i].sum;
                this._tableData[i][6] =  this._orderList[i].paid ? "1" : "2";
                this._tableData[i][7] =  this._orderList[i].productCategoryName;
                this._tableData[i][8] = this._orderTableBody.find("tr").last()[0];
            }
            this._orderTableStateButton = $(ShoppingCardSelectors.orderTableStateButton);
            this._orderTableDeleteButton = $(ShoppingCardSelectors.orderTableDeleteButton);

            this._orderTableStateButton.on("click", event => this.switchOrderState(event));
            this._orderTableDeleteButton.on("click", event => this.deleteOrder(event));
            this._orderTableDeleteButton.hover(function(){
                let elements = $(this).closest("tr").find("td");
                for(let i = 0; i < elements.length; i++)
                    $(elements[i]).addClass("sc-orderTable-deleteIndicator")},function(){
                let elements = $(this).closest("tr").find("td");
                for(let i = 0; i < elements.length; i++)
                    $(elements[i]).removeClass("sc-orderTable-deleteIndicator")});
            this.setSummaryData();
            this.sort(0);
        }


        private deleteOrder(event: JQueryEventObject): void
        {
            const orderId = parseInt($(event.currentTarget)
                .closest("tr")
                .attr("orderid"));

            $(event.currentTarget).closest("tr").find('td').addClass("hide");
            $(event.currentTarget).removeClass("hide");
            $(event.currentTarget).removeClass("sc-orderTable-trashIcon").addClass("loading");

            ShoppingCardService.deleteOrder(orderId, success =>
            {
                $(event.currentTarget).removeClass("loading").addClass("sc-orderTable-trashIcon");
                $(event.currentTarget).closest("tr").find('td').removeClass("hide");
                this._orderSummary[this._tableData[$(event.currentTarget).closest("tr").index()][7]]--;
                this._tableData.splice($(event.currentTarget).closest("tr").index(),1);
                $(event.currentTarget).closest("tr").remove();
                this.setSummaryData();
            });
        }
        private switchOrderState(event: JQueryEventObject): void
        {
            const orderId = parseInt($(event.currentTarget)
                .closest("tr")
                .attr("orderid"));

            let updateUserOrder: IUserOrderPaid = {
                paid: !$(event.currentTarget).hasClass("sc-orderTable-paidIcon")
            };

            $(event.currentTarget).closest("tr").find('td').addClass("hide");
            $(event.currentTarget).removeClass("hide");
            $(event.currentTarget).toggleClass("sc-orderTable-outstandingIcon",false).toggleClass("sc-orderTable-paidIcon",false).addClass("loading");

            ShoppingCardService.markOrder(orderId,updateUserOrder, success =>
            {
                $(event.currentTarget).closest("tr").find('td').removeClass("hide");
                $(event.currentTarget).removeClass("loading");
                $(event.currentTarget).toggleClass("sc-orderTable-outstandingIcon",!updateUserOrder.paid).toggleClass("sc-orderTable-paidIcon",updateUserOrder.paid);
                this._tableData[$(event.currentTarget).closest("tr").index()][6] = updateUserOrder.paid ? "1" : "2";
                $(event.currentTarget).closest("tr").addClass(updateUserOrder.paid ? "sc-orderTable-green-blink" : "sc-orderTable-red-blink");
                setTimeout(() =>
                {
                    $(event.currentTarget).closest("tr").removeClass(updateUserOrder.paid ? "sc-orderTable-green-blink" : "sc-orderTable-red-blink");
                }, 500);
            }
            );
        }

        private sortByColumn(a,b){
            if(this._currentSortColumn == 1 || this._currentSortColumn == 5)
                return parseFloat(a[this._currentSortColumn])-parseFloat(b[this._currentSortColumn])
            else
                return a[this._currentSortColumn]>b[this._currentSortColumn] ? 1 : a[this._currentSortColumn]<b[this._currentSortColumn] ? -1 : 0;
        }

        private sort(column: number): void
        {
            if(this._currentSortColumn == column) {
                let ascSort = $(this._orderTableSortButton[column]).hasClass("sc-orderTable-sortUpIcon");
                $(this._orderTableSortButton[column]).toggleClass("sc-orderTable-sortUpIcon",!ascSort).toggleClass("sc-orderTable-sortDownIcon",ascSort);
                this._tableData.reverse();
            } else {
                for(let i = 0; i < this._orderTableSortButton.length; i++)
                    $(this._orderTableSortButton[i]).removeClass("sc-orderTable-sortUpIcon").removeClass("sc-orderTable-sortDownIcon").addClass("sc-orderTable-sortIcon");
                $(this._orderTableSortButton[column]).removeClass("sc-orderTable-sortIcon").addClass("sc-orderTable-sortUpIcon");
                this._currentSortColumn = column;
                this._tableData = this._tableData.sort((a,b) => this.sortByColumn(a,b));
            }
            for(let i = 0; i < this._tableData.length; i++)
                this._orderTableBody.append(this._tableData[i][this._tableData[i].length-1]);
        }
    }
}

