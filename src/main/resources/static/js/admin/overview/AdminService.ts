/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../../share/Constants.ts" />
/// <reference path="../../share/WebServiceAccess.ts" />
/// <reference path="../productCatalog/IProductCatalog.ts" />
/// <reference path="IAdmin.ts" />

namespace WebApplication.Admin.Overview
{
    export class AdminService
    {
        public static loadBulkOrders(onSuccess: (bulkOrder: IBulkOrder[]) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.bulkOrder, onSuccess);
        }

        public static activateBulkOrder(bulkOrder: IBulkOrder, onSuccess: (bulkOrder: IBulkOrder) => void, onError: (xhr: any) => void): void
        {
            WebServiceAccess.ajaxPost(WebService.bulkOrder,bulkOrder, onSuccess, onError);
        }

        public static deactivateBulkOrder(id: number, onSuccess: (success: IOnSuccess) => void): void
        {
            WebServiceAccess.ajaxDelete(WebService.bulkOrder,id, onSuccess);
        }

        public static loadProductCatalogs(onSuccess: (productCatalog: IProductCatalog[]) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.productCatalog, onSuccess);
        }

        public static uploadProductCatalog(formData: any, onSuccess: (success: IOnSuccess) => void, onError: (xhr: any) => void): void
        {
            WebServiceAccess.ajaxPostMultipart(WebService.adminProductCatalog + "/upload",formData, onSuccess, onError);
        }

        public static getDeliveryData(onSuccess: (deliveryData: IDeliveryData) => void): void
        {
            // Todo: @Sascha & @Daniel => Request definieren und testen
            WebServiceAccess.ajaxGet(WebService.deliveryData, onSuccess);
        }

        public static saveDeliveryData(deliveryData: IDeliveryData, onSuccess: (success: IOnSuccess) => void): void
        {
            // Todo: @Sascha & @Daniel => Request definieren und testen
            WebServiceAccess.ajaxPut(WebService.deliveryData,deliveryData, onSuccess);
        }
    }
}



