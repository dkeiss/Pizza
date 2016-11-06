/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="IUserOrder.ts" />
/// <reference path="ShoppingCardService.ts" />

namespace WebApplication.Admin.ShoppingCard
{
    $(document).ready(() =>
    {
        new TableController();
    });

    export class TableController {
        private _orderTable: JQuery = null;
        private _orderTableBody: JQuery = null;
        private _orderTableSortButton: JQuery = null;
        private _orderTableStateButton: JQuery = null;
        private _orderTableDeleteButton: JQuery = null;

        private _currentSortColumn: number;

        private _tableData: any;

        private _orderList: IUserOrder[] = [
            {orderId:7,firstName: "Max", lastName: "Mustermann", productId: 1, productName: "Pizza Salami", productAdditions: "Peperonie, Kapern", productVariation: "Groß", productPrice: "7,53 €", paid: false},
            {orderId:8,firstName: "Rosa", lastName: "Schlüpfer", productId: 4, productName: "Calzone", productAdditions: "", productVariation: "Normal", productPrice: "6,50 €", paid: false},
            {orderId:9,firstName: "Chris P.", lastName: "Bacon", productId: 17, productName: "Pizza Schinken", productAdditions: "", productVariation: "Groß", productPrice: "9,50 €", paid: false},
            {orderId:10,firstName: "Peter", lastName: "Sile", productId: 12, productName: "Nudeln", productAdditions: "", productVariation: "Normal", productPrice: "12,00 €", paid: false},
            {orderId:11,firstName: "Wilma", lastName: "Bierholen", productId: 3, productName: "Pizza Diavolo", productAdditions: "Jalapenos", productVariation: "Klein", productPrice: "7,53 €", paid: false},
            {orderId:12,firstName: "Axel", lastName: "Schweiß", productId: 4, productName: "Salat", productAdditions: "Ohne Ei", productVariation: "Normal", productPrice: "5,99 €", paid: false}
        ];


        constructor()
        {
            this._currentSortColumn = -1;
            this._orderTable = $(ShoppingCardSelectors.orderTable);
            this._orderTableBody = this._orderTable.find("tbody");
            this._orderTableSortButton = $(ShoppingCardSelectors.orderTableSortButton);

            for(let i = 0; i <this._orderTableSortButton.length; i++) {
                $(this._orderTableSortButton[i]).on("click", () => this.sort(i));
            }
            console.log("consti");
            this.getUserOrders();
            this.createTable();
        }

        private getUserOrders() : void {
            console.log(this._orderList);
            /*ShoppinCardService.loadUserOrders(orderList =>
            {
                this._orderList = orderList;
            });*/
        }

        private createTable() : void {

            this._tableData = new Array(this._orderList.length);

            for(let i = 0; i < this._orderList.length; i++) {
                let element = "";
                element += "<tr userid='" + this._orderList[i].orderId + "'>";
                element += "<td >" +  this._orderList[i].firstName + " " + this._orderList[i].lastName + "</td>";
                element += "<td >" +  this._orderList[i].productId + "</td>";
                element += "<td >" +  this._orderList[i].productName + "</td>";
                element += "<td >" +  this._orderList[i].productAdditions + "</td>";
                element += "<td >" +  this._orderList[i].productVariation + "</td>";
                element += "<td >" +  this._orderList[i].productPrice + "</td>";
                element += "<td class='"+  (this._orderList[i].paid ? "sc-orderTable-paidIcon" : "sc-orderTable-outstandingIcon") + " card-orderTable_stateButton'></td>";
                element += "<td class='sc-orderTable-trashIcon card-orderTable_deleteButton'></td>";
                element += "</tr>";
                this._orderTableBody.append(element);

                this._tableData[i] = new Array(9);
                this._tableData[i][0] =  this._orderList[i].firstName + " " + this._orderList[i].lastName;
                this._tableData[i][1] =  this._orderList[i].productId;
                this._tableData[i][2] =  this._orderList[i].productName;
                this._tableData[i][3] =  this._orderList[i].productAdditions;
                this._tableData[i][4] =  this._orderList[i].productVariation;
                this._tableData[i][5] =  this._orderList[i].productPrice.replace(",",".").replace(" €", "");
                this._tableData[i][6] =  this._orderList[i].paid ? "1" : "2";
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

            /*let rows = this._orderTableBody.find("tr");
            this._tableData = new Array(rows.length);
            for(let i = 0; i <rows.length; i++) {
                let columns = $(rows[i]).find("td");
                this._tableData[i] = new Array(columns.length+1);
                for(let j = 0; j <columns.length; j++) {
                    if(j==1 || j == 5)
                        this._tableData[i][j] =  columns[j].innerText.replace(",",".").replace(" €", "");
                    else if(j==6)
                        this._tableData[i][j] = $(columns[j]).hasClass("sc-orderTable-paidIcon") ? "1" : "2";
                    else
                        this._tableData[i][j] = columns[j].innerText;
                }
                this._tableData[i][columns.length] =
            }*/
            this.sort(0);
        }


        private deleteOrder(event: JQueryEventObject): void
        {
            const orderId = parseInt($(event.currentTarget)
                .closest("tr")
                .attr("orderid"));

            ShoppingCardService.deleteOrder(orderId, success =>
            {
                this._tableData.splice($(event.currentTarget).closest("tr").index(),1);
                $(event.currentTarget).closest("tr").remove();
            });
        }
        private switchOrderState(event: JQueryEventObject): void
        {
            var paid: boolean;
            const orderId = parseInt($(event.currentTarget)
                .closest("tr")
                .attr("orderid"));
            paid = $(event.currentTarget).hasClass("sc-orderTable-paidIcon");
            let updateUserkOrder: IUserOrder = null;

            ShoppingCardService.markOrder(updateUserkOrder, success =>
            {
                $(event.currentTarget).toggleClass("sc-orderTable-outstandingIcon",paid).toggleClass("sc-orderTable-paidIcon",!paid);
                this._tableData[$(event.currentTarget).closest("tr").index()][6] = !paid ? "1" : "2";
                $(event.currentTarget).closest("tr").addClass(!paid ? "sc-orderTable-green-blink" : "sc-orderTable-red-blink");
                setTimeout(() =>
                {
                    $(event.currentTarget).closest("tr").removeClass(!paid ? "sc-orderTable-green-blink" : "sc-orderTable-red-blink");
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

