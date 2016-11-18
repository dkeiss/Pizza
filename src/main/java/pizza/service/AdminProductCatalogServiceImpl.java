package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.service.common.ObjectMapperUtil;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.List;

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
        List<AdditionalCategoryVO> additionals = productCatalogFull.getAdditionals();
        if (additionals != null) {
            addAdditionals(additionals);
        }
        ProductCatalogVO productCatalog = productCatalogService.createProductCatalog(productCatalogFull.getProductCatalog());
        return ObjectMapperUtil.copyFromBusinessObject(productCatalog, new ProductCatalogInfoVO());
    }

    public void addAdditionals(List<AdditionalCategoryVO> additionals) {
        for (AdditionalCategoryVO additionalCategory : additionals) {
            additionalService.createAdditionalCategory(additionalCategory);
        }
    }

}
