package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.ProductCatalog;
import pizza.repositories.ProductCatalogRepository;
import pizza.service.common.ObjectMapperService;
import pizza.service.exception.NotFoundException;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 29.10.2016.
 */
@Service
public class ProductCatalogServiceImpl implements ProductCatalogService, ObjectMapperService {

    @Autowired
    private ProductCatalogRepository productCatalogRepository;

    @Override
    public ProductCatalogVO createProductCatalog(ProductCatalogVO menuVO) {
        ProductCatalog productCatalog = copyFromValueObject(menuVO, new ProductCatalog());
        productCatalog = productCatalogRepository.save(productCatalog);
        menuVO = copyFromBusinessObject(productCatalog, menuVO);
        return menuVO;
    }

    @Override
    public ProductCatalogVO getProductCatalog(Integer id) {
        ProductCatalog productCatalog = productCatalogRepository.findOne(id);
        if (productCatalog == null) {
            throw new NotFoundException();
        }
        return copyFromBusinessObject(productCatalog, new ProductCatalogVO());
    }

    @Override
    public List<ProductCatalogInfoVO> listProductCataloges() {
        return copyListFromBusinessObject(productCatalogRepository.findAll(), ProductCatalogInfoVO.class);
    }

}
