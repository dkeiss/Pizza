package pizza.service;

import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 29.10.2016.
 */
public interface ProductCatalogService {

    ProductCatalogVO createProductCatalog(ProductCatalogVO menuVO);

    ProductCatalogVO getProductCatalog(Integer productCatalogId);

    List<ProductCatalogInfoVO> listProductCataloges();

    boolean productCatalogExists(Integer productCatalogId);

    ProductCatalogVO getActiveProductCatalog();

    boolean productExists(Integer productId);

    boolean productVariationExists(Integer productVariationId);

    Product findProduct(Integer productId);

    ProductVariation findProductVariation(Integer productVariationId);

}
