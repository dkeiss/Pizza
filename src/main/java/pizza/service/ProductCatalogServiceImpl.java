package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.ProductCatalog;
import pizza.repositories.ProductCatalogRepository;
import pizza.service.common.ObjectMapperService;
import pizza.service.exception.NotFoundException;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.Date;
import java.util.List;

import static pizza.service.common.ProductBusinessToValueObjectConverter.getProductCatalogFromBO;
import static pizza.service.common.ProductValueToBusinessObjectConverter.getProductCategoriesFromVO;

/**
 * Created by Daniel Keiss on 29.10.2016.
 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService, ObjectMapperService {

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
    public ProductCatalogVO getProductCatalog(Integer id) {
        ProductCatalog productCatalog = productCatalogRepository.findOne(id);
        if (productCatalog == null) {
            throw new NotFoundException();
        }
        return getProductCatalogFromBO(productCatalog);
    }

    @Override
    public List<ProductCatalogInfoVO> listProductCataloges() {
        return copyListFromBusinessObject(productCatalogRepository.findAll(), ProductCatalogInfoVO.class);
    }

}
