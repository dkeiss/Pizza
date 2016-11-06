package pizza.service;

import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
public interface AdminProductCatalogService {

    ProductCatalogInfoVO createProductCatalogFull(ProductCatalogFullVO productCatalogFull);

}
