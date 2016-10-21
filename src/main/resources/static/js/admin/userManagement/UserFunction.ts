/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />

namespace WebApplication.Admin.UserManagement
{
    $(document).ready(() =>
    {
        const webApplication = new UserFunction();
        webApplication.start();
    });


    export class UserFunction
    {
        private _userList: IUserList = null;

        constructor()
        {
            UserService.loadUserTable(userList =>
            {
                this._userList = userList;
            });
        }

        public start()
        {

        }
    }
}

