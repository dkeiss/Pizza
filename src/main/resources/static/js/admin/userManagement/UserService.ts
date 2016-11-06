/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/WebServiceAccess.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class UserService
    {
        public static loadUserTable(onSuccess: (userList: IUserList) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.user, onSuccess);
        }

        public static sendNewUser(sendNewUser: IAddNewUser, onSuccess: () => void): void
        {
            WebServiceAccess.ajaxPut(WebService.user, sendNewUser, onSuccess);
        }

        public static sendEditUser(sendEditUser: IEditUser, onSuccess: (success: IOnSuccess) => void): void
        {
            WebServiceAccess.ajaxPut(WebService.user + "/" + sendEditUser.userId, sendEditUser, onSuccess);
        }

        public static deleteUser(sendUserId: number, onSuccess: (success: IOnSuccess) => void): void
        {
            WebServiceAccess.ajaxDelete(WebService.user, sendUserId, onSuccess);
        }
    }
}