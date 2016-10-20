/// <reference path="../../jquery.d.ts" />
/// <reference path="../../Constants.ts" />
/// <reference path="IUser.ts" />

namespace WebApplication.Admin.UserManagement
{
    export class UserService
    {
        public static loadUserTable(onSuccess: (userList: IUserList) => void)
        {
            this.ajaxGet("users", onSuccess);
        }

        private static ajaxGet(serviceMethod: string, onSuccess: (xhr: any) => void)
        {
            $.ajax({
                type: "GET",
                url: WebService.url + serviceMethod,
                scriptCharset: "utf-8",
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                async: false,
                success: onSuccess
            });
        }
    }
}