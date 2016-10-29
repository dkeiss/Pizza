/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class TableController
    {
        private _userList: IUserList[] = null;
        private _userTableSelector: JQuery = null;
        private _userTableRowSelector: JQuery = null;

        constructor()
        {
            this._userTableSelector = $(UserManagementSelectors.userTable);

            this.updateUserList();
            this.createTable();

            this._userTableRowSelector = $(UserManagementSelectors.userTableCell);
        }

        public start(): void
        {
            this._userTableRowSelector.on("click", event => this.activeTableEdit(event));
        }

        private updateUserList(): void
        {
            UserService.loadUserTable(userList =>
            {
                this._userList = userList;
            });
        }

        private createTable(): void
        {
            const userList = this._userList;
            let element = "";

            for(let i = 0; i < userList.length; i++)
            {
                element += "<tr userid='" + userList[i].id + "'>";

                if (userList[i].admin === true)
                {
                    element += "<td><input type='checkbox' checked='checked' /></td>"
                }
                else
                {
                    element += "<td><input type='checkbox' /></td>"
                }

                element += "<td class='edit'><span>" + userList[i].firstName + "</span></td>";
                element += "<td class='edit'><span>" + userList[i].lastName + "</span></td>";
                element += "<td class='edit'><span></span></td>";
                element += "<td class='edit'><span>" + userList[i].discount + "</span></td>";
                element += "<td>" +
                        "<div  class='bla'>" +
                            "<div class='um-userTable-confirmIcon " + UserManagementSelectors.userTableConfirmEdit + "'></div>" +
                        "</div>" +
                        "<div class='" + UserManagementCss.userTableHiddenIcon + "'>" +
                            "<div class='um-userTable-confirmIcon " + UserManagementSelectors.userTableConfirmEdit + "'></div>" +
                            "<div class='um-userTable-aboardIcon " + UserManagementSelectors.userTableAboardEdit + "'></div>" +
                        "</div>"
                        "</td>";
                element += "</tr>";
            }

            this._userTableSelector.append(element);
        }

        private activeTableEdit(event: JQueryEventObject): void
        {
            const target = event.currentTarget;

            if (!$(target).hasClass("edit")) return;


            if (!$(target).hasClass(UserManagementCss.userTableActiveCellEdit))
            {
                // Input inputField and hide text
                $(target)
                    .addClass(UserManagementCss.userTableActiveCellEdit)
                    .append(
                        $("<input>", {
                            type: "text",
                            val: $(event.currentTarget).text()
                        })
                    );

                // Disable all another tr in table
                $(target)
                    .closest("table")
                    .find("tr")
                    .next()
                    .each( (index, element) =>
                    {
                        if (!$(element).find("td").hasClass(UserManagementCss.userTableActiveCellEdit))
                        {
                            $(element).addClass(UserManagementCss.userTableDisableTrSelections);
                        }
                    });

                // Show confirm & abort button-icons
                $(target)
                    .closest("tr")
                    .find("td")
                    .last()
                    .removeClass(UserManagementCss.userTableHiddenIcon)
                    .addClass(UserManagementCss.userTableShowIcon);
            }
        }
    }
}