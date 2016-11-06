/// <reference path="../../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/WebServiceAccess.ts" />
/// <reference path="../productCatalog/IProductCatalog.ts" />
/// <reference path="IAdmin.ts" />

namespace WebApplication.Admin.Overview
{
    export class AdminService
    {
        public static loadBulkOrders(onSuccess: (bulkOrder: IBulkOrder[]) => void): void
        {
            console.log("loadingbulkorder");
            WebServiceAccess.ajaxGet(WebService.bulkOrder, onSuccess);
        }

        public static activateBulkOrder(bulkOrder: IBulkOrder, onSuccess: (bulkOrder: IBulkOrder) => void, onError: (xhr: any) => void): void
        {
            console.log("post");
            WebServiceAccess.ajaxPost(WebService.bulkOrder,bulkOrder, onSuccess, onError);
        }

        public static deactivateBulkOrder(id: number, onSuccess: (bulkOrder: any) => void): void
        {
            console.log("delete");
            WebServiceAccess.ajaxDelete(WebService.bulkOrder,id, onSuccess);
        }

        public static loadProductCatalogs(onSuccess: (productCatalog: IProductCatalog[]) => void): void
        {
            console.log("loadingmenu");
            WebServiceAccess.ajaxGet(WebService.productCatalog, onSuccess);
        }
    }
}



