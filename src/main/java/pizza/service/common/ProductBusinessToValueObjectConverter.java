package pizza.service.common;

import pizza.domain.product.*;
import pizza.vo.product.menu.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 30.10.2016.
 */
public class ProductBusinessToValueObjectConverter {

    public static ProductCatalogVO getProductCatalogFromBO(ProductCatalog productCatalog) {
        ProductCatalogVO productCatalogVO = new ProductCatalogVO();
        productCatalogVO.setProductCatalogId(productCatalog.getProductCatalogId());
        productCatalogVO.setName(productCatalog.getName());
        productCatalogVO.setCreationDate(productCatalog.getCreationDate());
        productCatalogVO.setProductCategories(getProductCategoriesFromBO(productCatalog));
        return productCatalogVO;
    }

    public static List<ProductCategoryVO> getProductCategoriesFromBO(ProductCatalog productCatalog) {
        List<ProductCategoryVO> productCategorieVOs = new ArrayList<>();
        for (ProductCategory productCategory : productCatalog.getProductCategories()) {
            ProductCategoryVO productCategoryVO = new ProductCategoryVO();
            productCategoryVO.setProductCategoryId(productCategory.getProductCategoryId());
            productCategoryVO.setName(productCategory.getName());
            productCategoryVO.setCreationDate(productCategory.getCreationDate());
            productCategoryVO.setProductGroups(getProductGroupsFromBO(productCategory));
            productCategorieVOs.add(productCategoryVO);
        }
        return productCategorieVOs;
    }

    private static List<ProductGroupVO> getProductGroupsFromBO(ProductCategory productCategory) {
        List<ProductGroupVO> productGroupVOs = new ArrayList<>();
        for (ProductGroup productGroup : productCategory.getProductGroups()) {
            ProductGroupVO productGroupVO = new ProductGroupVO();
            productGroupVO.setProductGroupId(productGroup.getProductGroupId());
            productGroupVO.setName(productGroup.getName());
            productGroupVO.setCreationDate(productGroup.getCreationDate());
            productGroupVO.setProducts(getProductsFromBO(productGroup));
            productGroupVOs.add(productGroupVO);
        }
        return productGroupVOs;
    }

    private static List<ProductVO> getProductsFromBO(ProductGroup productGroup) {
        List<ProductVO> productVOs = new ArrayList<>();
        for (Product product : productGroup.getProducts()) {
            ProductVO productVO = new ProductVO();
            productVO.setProductId(product.getProductId());
            productVO.setName(product.getName());
            productVO.setDescription(product.getDescription());
            productVO.setNumber(product.getNumber());
            productVO.setCreationDate(product.getCreationDate());
            productVO.setProductVariations(getProductVariationsFromBO(product));
            productVOs.add(productVO);
        }
        return productVOs;
    }

    private static List<ProductVariationVO> getProductVariationsFromBO(Product product) {
        ArrayList<ProductVariationVO> productVariationVOs = new ArrayList<>();
        for (ProductVariation productVariation : product.getProductVariations()) {
            ProductVariationVO productVariationVO = new ProductVariationVO();
            productVariationVO.setProductVariationId(productVariation.getProductVariationId());
            productVariationVO.setName(productVariation.getName());
            productVariationVO.setPrice(productVariation.getPrice());
            productVariationVO.setCreationDate(productVariation.getCreationDate());
            productVariationVOs.add(productVariationVO);
        }
        return productVariationVOs;
    }

}
