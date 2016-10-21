/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class TableController
    {
        private _userList: IUserList = null;

        constructor()
        {
            this.updateUserList();
        }

        public start(): void
        {

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
            let elementTr = "";


        }
    }
}