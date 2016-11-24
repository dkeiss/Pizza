/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class TableController
    {
        private _userList: IUserList = null;
        private _findUserSelector: JQuery = null;
        private _userTableSelector: JQuery = null;
        private _userTableRowSelector: JQuery = null;
        private _userTableDeleteUser: JQuery = null;
        private _userTableConfirmEdit: JQuery = null;
        private _userTableAboardEdit: JQuery = null;

        private _cssActiveCellEdit = "um-userTable-activeCellEdit";
        private _cssShowEditIcon = "um-userTable-showIcon";
        private _cssHiddenEditIcon = "um-userTable-hiddenEditIcon";
        private _cssDisableSelections = "um-userTable-disableSelections";


        private _confirmationDialogContainer: JQuery = null;
        private _confirmationDialogMessage: JQuery = null;
        private _confirmationDialogAccept: JQuery = null;
        private _confirmationDialogCancel: JQuery = null;
        private _confirmationDialogClose: JQuery = null;

        private _currentEvent: JQueryEventObject = null;

        private _cssShowContainer = "confirmationDialog-showContainer";


        constructor()
        {
            this._confirmationDialogContainer = $(UserManagementSelectors.confirmationDialogContainer);
            this._confirmationDialogMessage = $(UserManagementSelectors.confirmationDialogMessage);
            this._confirmationDialogAccept = $(UserManagementSelectors.confirmationDialogAccept);
            this._confirmationDialogCancel = $(UserManagementSelectors.confirmationDialogCancel);
            this._confirmationDialogClose = $(UserManagementSelectors.confirmationDialogClose);

            this._confirmationDialogMessage.text("Möchten Sie den Benutzer wirklich löschen?");
            this._confirmationDialogClose.on("click", () => {this._confirmationDialogContainer.removeClass(this._cssShowContainer);this._currentEvent=null;});
            this._confirmationDialogCancel.on("click", () => {this._confirmationDialogContainer.removeClass(this._cssShowContainer);this._currentEvent=null;});
            this._confirmationDialogAccept.on("click", () => {this._confirmationDialogContainer.removeClass(this._cssShowContainer);this.deleteSelectedUser(this._currentEvent);this._currentEvent=null;});

            this._findUserSelector = $(UserManagementSelectors.inputFieldFindUser);
            this._userTableSelector = $(UserManagementSelectors.userTable);
            this.refreshConstructor()
        }

        public refreshConstructor()
        {
            this.updateUserList();
            this.createTable();
        }

        public start(): void
        {
            this._userTableRowSelector = $(UserManagementSelectors.userTableCell);
            this._userTableDeleteUser = $(UserManagementSelectors.userTableDeleteUser);
            this._userTableAboardEdit = $(UserManagementSelectors.userTableAboardEdit);
            this._userTableConfirmEdit = $(UserManagementSelectors.userTableConfirmEdit);

            this._findUserSelector.on("keyup", event => this.findUserInList(event));
            this._userTableRowSelector.on("click", event => this.activeTableEdit(event));
            this._userTableDeleteUser.on("click", event => { this._currentEvent = event; this._confirmationDialogContainer.addClass(this._cssShowContainer);});
            //this._userTableDeleteUser.on("click", event => this.deleteSelectedUser(event));
            this._userTableConfirmEdit.on("click", event => this.rowConfirmIcon(event));
            this._userTableAboardEdit.on("click", event => this.rowAboardIcon(event));
        }

        private updateUserList(): void
        {
            UserService.loadUserTable(userList =>
            {
                this._userList = userList;
            });
        }

        private createTable(newUserList?: IUserList): void
        {
            const userList = newUserList == null ? this._userList : newUserList;

            let element = "";

            for(let i = 0; i < userList.length; i++)
            {
                element += "<tr userid='" + userList[i].userId + "'>";

                if (userList[i].admin === true)
                {
                    element += "<td class='checkBox'><input type='checkbox' checked='checked' class='isChecked' /></td>"
                }
                else
                {
                    element += "<td class='checkBox'><input type='checkbox' /></td>"
                }

                element += "<td class='edit firstName'><span>" + userList[i].firstName + "</span></td>";
                element += "<td class='edit lastName'><span>" + userList[i].lastName + "</span></td>";
                element += "<td class='email'><span>" + userList[i].userName + "</span></td>";
                element += "<td class='number discount'><span>" + userList[i].discount + "</span></td>";
                element += "<td class='" + this._cssHiddenEditIcon + "'>" +
                            "<div class='um-userTable-iconSingle um-userTable-deleteIcon um-userTable_deleteUser'></div>" +
                            "<div class='um-userTable-iconMulti um-userTable-confirmIcon um-userTable_confirmEdit'></div>" +
                            "<div class='um-userTable-iconMulti um-userTable-aboardIcon um-userTable_aboardEdit'></div>" +
                        "</td>";
                element += "</tr>";
            }

            this._userTableSelector
                .find("tr")
                .next()
                .remove()
                .end().end()
                .append(element);

        }

        private activeTableEdit(event: JQueryEventObject): void
        {
            const target = event.currentTarget;

            if ($(target).hasClass(this._cssActiveCellEdit)) return;
            if (!$(target).hasClass("checkBox"))
            {
                if (!$(target).hasClass("edit"))
                {
                    if (!$(target).hasClass("email"))
                    {
                        if (!$(target).hasClass("number")) return;
                    }
                }
            }

            if ($(target).hasClass("checkBox"))
            {
                $(target).addClass(this._cssActiveCellEdit);
            }

            if ($(target).hasClass("edit"))
            {
                // Input inputField and hide text
                $(target)
                    .addClass(this._cssActiveCellEdit)
                    .append(
                        $("<input>", {
                            type: "text",
                            val: $(event.currentTarget).text()
                        })
                    );
            }

            if ($(target).hasClass("email"))
            {
                // Input inputField and hide text
                $(target)
                    .addClass(this._cssActiveCellEdit)
                    .append(
                        $("<input>", {
                            type: "text",
                            required: "required",
                            val: $(event.currentTarget).text()
                        })
                    );
            }

            if ($(target).hasClass("number"))
            {
                $(target)
                    .addClass(this._cssActiveCellEdit)
                    .append(
                        $("<input>", {
                            type: "number",
                            min: "0",
                            step: "0.1",
                            required: "required",
                            val: $(event.currentTarget).text()
                        })
                    );
            }

            // Disable all another tr in table
            this.disableActiveAllTr(target, true);
            // Show confirm & abort button-icons
            this.showHiddenIconsButton(target, true);
            // Change name from "Löschen" to "Bearbeiten"
            this.changeTextInLastTh("bearbeiten");
        }

        private rowConfirmIcon(event: JQueryEventObject): void
        {
            const elementTr = $(event.currentTarget).closest("tr");
            var editUser = new RequestEditUser();

            editUser.userId = parseInt(elementTr.attr("userid"));
            editUser.firstName = this.getRowTdInputText(elementTr, "td.firstName");
            editUser.lastName = this.getRowTdInputText(elementTr, "td.lastName");
            editUser.userName = this.getRowTdInputText(elementTr, "td.email");
            editUser.discount = parseFloat(this.getRowTdInputText(elementTr, "td.discount"));
            editUser.admin = this.getRowTdCheckBox(elementTr);

            UserService.sendEditUser(editUser, success =>
            {
                if (success) this.requestSuccess();
            });
        }

        private getRowTdInputText(elementTr: any, findClass: string): string
        {
            let text: string = "";

            elementTr
                .find(findClass)
                .each((index, element) =>
                {
                    if ($(element).hasClass(this._cssActiveCellEdit))
                    {
                        text = $(element).find("input").first().val().trim();
                    }
                    else
                    {
                        text = $(element).text().trim();
                    }
                });

            return text;
        }

        private getRowTdCheckBox(elementTr: any): boolean
        {
            let isChecked: boolean = false;
            elementTr
                .find("td.checkBox")
                .children()
                .each((index, element) =>
                {
                    if (element.checked)
                    {
                        isChecked = true;
                    }
                });

            return isChecked;
        }

        private rowAboardIcon(event: JQueryEventObject): void
        {
            const target = event.currentTarget;

            $(target)
                .closest("tr")
                .find("td")
                .each( (index, element) => {
                    if ($(element).hasClass("checkBox"))
                    {
                        const checkBox = $(element)
                            .removeClass(this._cssActiveCellEdit)
                            .children();

                        if (checkBox.hasClass("isChecked"))
                        {
                            checkBox.prop("checked", true);
                        }
                        else
                        {
                            checkBox.prop("checked", false);
                        }
                    }

                    if ($(element).hasClass("edit") || $(element).hasClass("email") || $(element).hasClass("number"))
                    {
                        $(element)
                            .removeClass(this._cssActiveCellEdit)
                            .find("input")
                            .remove();
                    }
                });

            // Active all tr in table
            this.disableActiveAllTr(target, false);
            // Hidden confirm & abort button-icons
            this.showHiddenIconsButton(target, false);
            // Change name from "Bearbeiten" to "Löschen"
            this.changeTextInLastTh("löschen")
        }

        private disableActiveAllTr(target: any, status: boolean): void
        {
            const elementTr = $(target)
                .closest("table")
                .find("tr")
                .next();

            if (status)
            {
                elementTr
                    .each( (index, element) =>
                    {
                        if (!$(element).find("td").hasClass(this._cssActiveCellEdit))
                        {
                            $(element).addClass(this._cssDisableSelections);
                        }
                    });
            }
            else
            {
                elementTr
                    .each( (index, element) =>
                    {
                        $(element).removeClass(this._cssDisableSelections);
                    });
            }
        }

        private showHiddenIconsButton(target: any, status: boolean): void
        {
            const elementTr = $(target)
                .closest("tr")
                .find("td")
                .last();

            if (status)
            {
                elementTr
                    .removeClass(this._cssHiddenEditIcon)
                    .addClass(this._cssShowEditIcon);
            }
            else
            {
                elementTr
                    .removeClass(this._cssShowEditIcon)
                    .addClass(this._cssHiddenEditIcon);
            }
        }

        private changeTextInLastTh(name: string): void
        {
            this._userTableSelector
                .find("th:last-child")
                .text(name);
        }

        private deleteSelectedUser(event: JQueryEventObject): void
        {
            const userId = parseInt($(event.currentTarget)
                                        .closest("tr")
                                        .attr("userid"));

            UserService.deleteUser(userId, success =>
            {
                if (success) this.requestSuccess();
            });
        }

        private requestSuccess(): void
        {
            setTimeout(() =>
            {
                this.changeTextInLastTh("löschen")
                this.refreshConstructor();
                this.start();
            }, 200);
        }

        private findUserInList(event: JQueryEventObject): void
        {
            const findUser = $(event.currentTarget).val();

            if (findUser.length == 0)
            {
                this.createTable();
                this.start();
            }

            if (findUser.length >= 3)
            {
                let userList: IUserList = this._userList.filter(item => item.lastName.toLowerCase().indexOf(findUser.toLowerCase()) >= 0);
                this.createTable(userList);
                this.start();
            }
        }
    }

    export class RequestEditUser implements IEditUser
    {
        userId: number;
        firstName: string;
        lastName: string;
        userName: string;
        discount: number;
        admin: boolean;
    }
}