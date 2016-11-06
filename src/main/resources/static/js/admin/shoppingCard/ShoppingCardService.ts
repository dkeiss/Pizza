/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../Constants.ts" />
/// <reference path="../../WebServiceAccess.ts" />
/// <reference path="IUserOrder.ts" />

namespace WebApplication.Admin.ShoppingCard
{
    export class ShoppingCardService
    {
        public static loadUserOrders(onSuccess: (userOrder: IUserOrder[]) => void): void
        {
            console.log("loadinguserorder");
            WebServiceAccess.ajaxGet(WebService.userOrder, onSuccess);
        }

        public static deleteOrder(id: number, onSuccess: (userOrder: any) => void): void
        {
            console.log("delete");
            onSuccess(true);
            //WebServiceAccess.ajaxDelete(WebService.userOrder,id, onSuccess);
        }

        public static markOrder(updateUserOrder: IUserOrder, onSuccess: (success: boolean) => void): void
        {
            // Todo: @Simon & @Daniel => Request definieren und testen
            onSuccess(true);
            //WebServiceAccess.ajaxPut(WebService.userOrder + "/" + updateUserOrder.orderId, updateUserOrder); // 405 error "Method Not Allowed"
        }
    }
}