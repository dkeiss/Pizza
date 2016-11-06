/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/WebServiceAccess.ts" />
/// <reference path="IBulkOrder.ts" />
/// <reference path="../productCatalog/IProductCatalog.ts" />
/// <reference path="IErrorResponse.ts" />

namespace WebApplication.Admin.Overview
{
    export class AdminService
    {
        public static loadBulkOrders(onSuccess: (bulkOrder: IBulkOrder[]) => void): void
        {
            console.log("loadingbulkorder");
            WebServiceAccess.ajaxGet(WebService.url + "bulkorder", onSuccess);
        }

        public static activateBulkOrder(bulkOrder: IBulkOrder, onSuccess: (bulkOrder: IBulkOrder) => void, onError: (xhr: any) => void): void
        {
            console.log("post");
            WebServiceAccess.ajaxPost(WebService.url + "bulkorder",bulkOrder, onSuccess, onError);
        }

        public static deactivateBulkOrder(id: number, onSuccess: (bulkOrder: any) => void): void
        {
            console.log("delete");
            WebServiceAccess.ajaxDelete(WebService.url + "bulkorder",id, onSuccess);
        }

        public static loadProductCatalogs(onSuccess: (productCatalog: IProductCatalog[]) => void): void
        {
            console.log("loadingmenu");
            WebServiceAccess.ajaxGet(WebService.url + "productcatalog", onSuccess);
        }
    }
}



