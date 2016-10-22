/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../Constants.ts" />
/// <reference path="../../WebServiceAccess.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class UserService
    {
        public static loadUserTable(onSuccess: (userList: IUserList[]) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.url + "users", onSuccess);
        }
    }
}