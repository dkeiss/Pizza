/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="UserService.ts" />

namespace WebApplication.Admin.UserManagement
{
    $(document).ready(() =>
    {
        UserService.loadUserTable(userList =>
        {
            alert(userList[0].userName);
        });
    });
}