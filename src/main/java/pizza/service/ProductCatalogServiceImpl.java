package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.*;
import pizza.repositories.ProductCatalogRepository;
import pizza.service.common.ObjectMapperService;
import pizza.service.exception.NotFoundException;
import pizza.vo.product.menu.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

        ArrayList<ProductCategory> productCategories = new ArrayList<>();
        for (ProductCategoryVO productCategoryVO : productCatalogVO.getProductCategories()) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCreationDate(new Date());
            productCategory.setName(productCatalogVO.getName());
            ArrayList<ProductGroup> productGroups = new ArrayList<>();
            for (ProductGroupVO productGroupVO : productCategoryVO.getProductGroups()) {
                ProductGroup productGroup = new ProductGroup();
                productGroup.setName(productGroupVO.getName());
                productGroup.setCreationDate(new Date());
                ArrayList<Product> products = new ArrayList<>();
                for (ProductVO productVO : productGroupVO.getProducts()) {
                    Product product = new Product();
                    product.setName(productVO.getName());
                    product.setDescription(productVO.getDescription());
                    product.setNumber(productVO.getNumber());
                    ArrayList<ProductVariation> productVariations = new ArrayList<>();
                    for (ProductVariationVO productVariationVO : productVO.getProductVariations()) {
                        ProductVariation productVariation = new ProductVariation();
                        productVariation.setName(productVariationVO.getName());
                        productVariation.setPrice(productVariation.getPrice());
                        productVariations.add(productVariation);
                    }
                    product.setProductVariations(productVariations);
                    products.add(product);
                }
                productGroup.setProducts(products);
                productGroups.add(productGroup);
            }
            productCategory.setProductGroups(productGroups);
            productCategories.add(productCategory);
        }
        productCatalog.setProductCategories(productCategories);

        productCatalogRepository.save(productCatalog);
        return productCatalogVO;
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
