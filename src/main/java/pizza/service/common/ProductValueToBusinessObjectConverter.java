package pizza.service.common;

import pizza.domain.product.*;
import pizza.vo.product.menu.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Keiss on 30.10.2016.
 */
public class ProductValueToBusinessObjectConverter {

    public static List<ProductCategory> getProductCategoriesFromVO(ProductCatalogVO productCatalogVO, ProductCatalog productCatalog) {
        List<ProductCategory> productCategories = new ArrayList<>();
        for (ProductCategoryVO productCategoryVO : productCatalogVO.getProductCategories()) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setName(productCatalogVO.getName());
            productCategory.setProductCatalog(productCatalog);
            productCategory.setCreationDate(productCatalog.getCreationDate());
            productCategory.setProductGroups(getProductGroupsFromVO(productCategoryVO, productCategory));
            productCategories.add(productCategory);
        }
        return productCategories;
    }

    private static List<ProductGroup> getProductGroupsFromVO(ProductCategoryVO productCategoryVO, ProductCategory productCategory) {
        List<ProductGroup> productGroups = new ArrayList<>();
        for (ProductGroupVO productGroupVO : productCategoryVO.getProductGroups()) {
            ProductGroup productGroup = new ProductGroup();
            productGroup.setName(productGroupVO.getName());
            productGroup.setProductCategory(productCategory);
            productGroup.setCreationDate(productCategory.getCreationDate());
            productGroup.setProducts(getProductsFromVO(productGroupVO, productGroup));
            productGroups.add(productGroup);
        }
        return productGroups;
    }

    private static List<Product> getProductsFromVO(ProductGroupVO productGroupVO, ProductGroup productGroup) {
        List<Product> products = new ArrayList<>();
        for (ProductVO productVO : productGroupVO.getProducts()) {
            Product product = new Product();
            product.setName(productVO.getName());
            product.setDescription(productVO.getDescription());
            product.setNumber(productVO.getNumber());
            product.setProductGroup(productGroup);
            product.setCreationDate(productGroup.getCreationDate());
            product.setProductVariations(getProductVariationsFromVO(productVO, product));
            products.add(product);
        }
        return products;
    }

    private static List<ProductVariation> getProductVariationsFromVO(ProductVO productVO, Product product) {
        ArrayList<ProductVariation> productVariations = new ArrayList<>();
        for (ProductVariationVO productVariationVO : productVO.getProductVariations()) {
            ProductVariation productVariation = new ProductVariation();
            productVariation.setName(productVariationVO.getName());
            productVariation.setPrice(productVariationVO.getPrice());
            productVariation.setProduct(product);
            productVariation.setCreationDate(product.getCreationDate());
            productVariations.add(productVariation);
        }
        return productVariations;
    }

}
