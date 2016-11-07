/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../share/Constants.ts" />
/// <reference path="../../share/WebServiceAccess.ts" />
/// <reference path="IUserOrder.ts" />

namespace WebApplication.Admin.ShoppingCard
{
    export class ShoppingCardService
    {
        public static loadUserOrders(onSuccess: (userOrder: IUserOrder[]) => void): void
        {
            // Todo: @Sascha & @Daniel => Request definieren und testen
            console.log("loadinguserorder");
            WebServiceAccess.ajaxGet(WebService.shoppingCard, onSuccess);
        }

        public static deleteOrder(id: number, onSuccess: (success: IOnSuccess) => void): void
        {
            // Todo: @Sascha & @Daniel => Request definieren und testen
            //console.log("delete");
            //onSuccess(true);
            WebServiceAccess.ajaxDelete(WebService.shoppingCard,id, onSuccess);
        }

        public static markOrder(userOrderId: number, updateUserOrder: IUserOrderPaid,onSuccess: (success: IOnSuccess) => void): void
        {
            // Todo: @Sascha & @Daniel => Request definieren und testen
            //onSuccess(true);
            WebServiceAccess.ajaxPut(WebService.shoppingCard + "/" + userOrderId + "/paid", updateUserOrder, onSuccess);
        }
    }
}