package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.Product;
import pizza.domain.product.ProductCatalog;
import pizza.domain.product.ProductVariation;
import pizza.repositories.ProductCatalogRepository;
import pizza.repositories.ProductRepository;
import pizza.repositories.ProductVariationRepository;
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

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariationRepository productVariationRepository;

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
        ProductCatalog productCatalog = getProductCatalogBO(productCatalogId);
        return getProductCatalogFromBO(productCatalog);
    }

    @Override
    public ProductCatalog getProductCatalogBO(Integer productCatalogId) {
        ProductCatalog productCatalog = productCatalogRepository.findOne(productCatalogId);
        if (productCatalog == null) {
            throw new NotFoundException();
        }
        return productCatalog;
    }

    @Override
    public List<ProductCatalogInfoVO> listProductCataloges() {
        return copyListFromBusinessObject(productCatalogRepository.findAll(), ProductCatalogInfoVO.class);
    }

    @Override
    public boolean productCatalogExists(Integer productCatalogId) {
        return productCatalogRepository.exists(productCatalogId);
    }

    @Override
    public ProductCatalogVO getActiveProductCatalog() {
        ProductCatalog activeProductCatalogBO = getActiveProductCatalogBO();
        return getProductCatalogFromBO(activeProductCatalogBO);
    }

    @Override
    public ProductCatalog getActiveProductCatalogBO() {
        BulkOrderVO activeBulkOrder = bulkOrderService.getActiveBulkOrder();
        if (activeBulkOrder == null) {
            throw new NotFoundException();
        }
        return getProductCatalogBO(activeBulkOrder.getCatalogId());
    }

    @Override
    public boolean productExists(Integer productId) {
        return productRepository.exists(productId);
    }

    @Override
    public boolean productVariationExists(Integer productVariationId) {
        return productVariationRepository.exists(productVariationId);
    }

    @Override
    public Product findProduct(Integer productId) {
        return productRepository.findOne(productId);
    }

    @Override
    public ProductVariation findProductVariation(Integer productVariationId) {
        return productVariationRepository.findOne(productVariationId);
    }

}
