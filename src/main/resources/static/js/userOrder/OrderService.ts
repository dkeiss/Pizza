/// <reference path="../thirdParty/jquery.d.ts" />
/// <reference path="../share/Constants.ts" />
/// <reference path="../share/WebServiceAccess.ts" />
/// <reference path="IProductCatalog.ts" />

namespace WebApplication.UserOrder
{
    export class OrderService
    {
        public static loadProductCatalog(getProductCatalog: (productCatalog: IProductCatalog) => void): void
        {
            WebServiceAccess.ajaxGet(WebService.productCatalogActive, getProductCatalog);
        }
    }
}