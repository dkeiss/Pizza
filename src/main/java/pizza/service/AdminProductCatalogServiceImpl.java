package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.service.common.ObjectMapperUtil;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@Service
public class AdminProductCatalogServiceImpl implements AdminProductCatalogService {

    @Autowired
    private AdditionalService additionalService;

    @Autowired
    private ProductCatalogService productCatalogService;

    @Override
    public ProductCatalogInfoVO createProductCatalogFull(ProductCatalogFullVO productCatalogFull) {
        for (AdditionalCategoryVO additionalCategory : productCatalogFull.getAdditionals()) {
            additionalService.createAdditionalCategory(additionalCategory);
        }
        ProductCatalogVO productCatalog = productCatalogService.createProductCatalog(productCatalogFull.getProductCatalog());
        return ObjectMapperUtil.copyFromBusinessObject(productCatalog, new ProductCatalogInfoVO());
    }

}
