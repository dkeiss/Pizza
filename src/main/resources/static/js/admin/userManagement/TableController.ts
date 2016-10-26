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

            this._userTableRowSelector = $(UserManagementSelectors.userTableRow);
        }

        public start(): void
        {
            this._userTableRowSelector.on("click", event =>
            {
                if (!$(event.currentTarget).hasClass("edit")) return;

                $(event.currentTarget)
                    .append(
                        $("<input>", {
                            type: "text",
                            val: $(event.currentTarget).text()
                        })
                    );

                //$(event.currentTarget).find("td:last-child").addClass(UserManagementCss.userTableShowIcon);
            });
        }

        private updateUserList(): void
        {
            UserService.loadUserTable(userList =>
            {
                this._userList = userList;
            });
        }

        private createTable()
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

                element += "<td class='edit'>" + userList[i].firstName + "</td>";
                element += "<td class='edit'>" + userList[i].lastName + "</td>";
                element += "<td class='edit'></td>";
                element += "<td class='edit'>" + userList[i].discount + "</td>";
                element += "<td> X </td>";
                element += "<td> Y </td>";
                element += "</tr>";
            }

            this._userTableSelector.append(element);
        }
    }
}