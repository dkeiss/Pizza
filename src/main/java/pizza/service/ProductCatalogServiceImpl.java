package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.ProductCatalog;
import pizza.repositories.ProductCatalogRepository;
import pizza.service.common.ObjectMapperUtil;
import pizza.service.exception.NotFoundException;
import pizza.vo.order.BulkOrderVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.Date;
import java.util.List;

import static pizza.service.common.ObjectMapperUtil.copyListFromBusinessObject;
import static pizza.service.common.ProductBusinessToValueObjectConverter.getProductCatalogFromBO;
import static pizza.service.common.ProductValueToBusinessObjectConverter.getProductCategoriesFromVO;

/**
 * Created by Daniel Keiss on 29.10.2016.
 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService {

    @Autowired
    private BulkOrderService bulkOrderService;

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Override
    public ProductCatalogVO createProductCatalog(ProductCatalogVO productCatalogVO) {
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.setName(productCatalogVO.getName());
        productCatalog.setCreationDate(new Date());
        productCatalog.setProductCategories(getProductCategoriesFromVO(productCatalogVO, productCatalog));

        productCatalog = productCatalogRepository.save(productCatalog);

        return getProductCatalogFromBO(productCatalog);
    }

    @Override
    public ProductCatalogVO getProductCatalog(Integer productCatalogId) {
        ProductCatalog productCatalog = productCatalogRepository.findOne(productCatalogId);
        if (productCatalog == null) {
            throw new NotFoundException();
        }
        return getProductCatalogFromBO(productCatalog);
    }

    @Override
    public List<ProductCatalogInfoVO> listProductCataloges() {
        return copyListFromBusinessObject(productCatalogRepository.findAll(), ProductCatalogInfoVO.class);
    }

    @Override
    public boolean productCatalogExists(Integer productCatalogId) {
        return productCatalogRepository.findOne(productCatalogId) != null;
    }

    @Override
    public ProductCatalogVO getActiveProductCatalog() {
        BulkOrderVO activeBulkOrder = bulkOrderService.getActiveBulkOrder();
        if (activeBulkOrder == null) {
            throw new NotFoundException();
        }
        return getProductCatalog(activeBulkOrder.getCatalogId());
    }

}
