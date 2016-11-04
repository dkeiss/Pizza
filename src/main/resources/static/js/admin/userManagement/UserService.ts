/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../Constants.ts" />
/// <reference path="../../WebServiceAccess.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class UserService
    {
        public static loadUserTable(onSuccess: (userList: IUserList) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.user, onSuccess);
        }

        public static sendNewUser(sendNewUser: IAddNewUser)
        {
            WebServiceAccess.ajaxPut(WebService.user, sendNewUser);
        }

        public static sendEditUser(sendEditUser: IEditUser, onSuccess: (success: boolean) => void): void
        {
            // Todo: @Simon & @Daniel => Request definieren und testen
            //WebServiceAccess.ajaxPost(WebService.user, sendEditUser, onSuccess, (error) => {alert(error); console.log(error);});
            WebServiceAccess.ajaxPut(WebService.user, sendEditUser); // 405 error "Method Not Allowed"
        }

        public static deleteUser(sendUserId: number, onSuccess: (success: boolean) => void): void
        {
            // Todo: @Simon & @Daniel => Request definieren und testen
            //onSuccess(true);
            WebServiceAccess.ajaxDelete(WebService.user, sendUserId, (okay) => {console.log(okay)}); // 405 error "Method Not Allowed"
        }
    }
}