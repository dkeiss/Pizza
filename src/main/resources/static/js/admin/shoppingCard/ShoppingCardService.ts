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
            WebServiceAccess.ajaxGet(WebService.shoppingCard, onSuccess);
        }

        public static deleteOrder(id: number, onSuccess: (success: IOnSuccess) => void): void
        {
            WebServiceAccess.ajaxDelete(WebService.shoppingCard,id, onSuccess);
        }

        public static markOrder(userOrderId: number, updateUserOrder: IUserOrderPaid,onSuccess: (success: IOnSuccess) => void): void
        {
            WebServiceAccess.ajaxPut(WebService.shoppingCard + "/" + userOrderId + "/paid", updateUserOrder, onSuccess);
        }

        public static getDeliveryData(onSuccess: (deliveryData: IDeliveryData) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.deliveryData, onSuccess);
        }
    }
}