package pizza.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.vo.product.menu.*;
import pizza.vo.product.menu.oldDELETE.*;
import pizza.vo.user.UserVO;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Controller
@RequestMapping("rest/menu")
public class MenuController {

    private ObjectMapper objectMapper = new ObjectMapper();

    private MenuVO menuVO = testMenu();

    private MenuVO testMenu() {
        MenuVO menuVO = new MenuVO();
        menuVO.setName("Test Menu");
        menuVO.setId(1);
        return menuVO;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    MenuVO getMenu() throws IOException {
        return menuVO;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    MenuVO createMenu(@RequestBody MenuVO menuVO) throws IOException {
        this.menuVO = menuVO;
        return menuVO;
    }

    @RequestMapping(value = "full", method = RequestMethod.GET)
    public
    @ResponseBody
    MenuVO getMenuFull() throws IOException {
        String menu = new String(Files.readAllBytes(Paths.get("documentation/Speisekarte.json")));
        OldMenuVO oldMenuVO = objectMapper.readValue(menu, OldMenuVO.class);

        MenuVO menuVO = testMenu();
        List<ProductCatalogVO> productCatalogVOs = new ArrayList<>();
        for (OldProductCategoryVO oldProductCategoryVO : oldMenuVO.getProductCategories()) {
            ProductCatalogVO productCatalogVO = new ProductCatalogVO();
            productCatalogVO.setName(oldProductCategoryVO.getName());
            List<ProductCategoryVO> productCategoryVOs = new ArrayList<>();
            for (OldSubCategoryVO oldSubCategoryVO : oldProductCategoryVO.getSubCategories()) {
                ProductCategoryVO productCategoryVO = new ProductCategoryVO();
                productCategoryVO.setName(oldSubCategoryVO.getName());

                List<ProductGroupVO> productGroupVOs = new ArrayList<>();
                ProductGroupVO productGroupVO = new ProductGroupVO();
                if (oldSubCategoryVO.getProducts().size() > 0) {
                    productGroupVO.setName(oldSubCategoryVO.getProducts().get(0).getCategory());
                } else {
                    productGroupVO.setName(oldSubCategoryVO.getName());
                }
                productGroupVOs.add(productGroupVO);

                List<ProductVO> productVOs = new ArrayList<>();
                for (OldProductVO oldProductVO : oldSubCategoryVO.getProducts()) {
                    ProductVO productVO = new ProductVO();
                    productVO.setName(oldProductVO.getName());
                    productVO.setNumber(oldProductVO.getNumber());
                    productVO.setDescription(oldProductVO.getDescription());
                    List<ProductVariationVO> productVariationVOs = new ArrayList<>();
                    if (!StringUtils.isEmpty(oldProductVO.getPrice())) {
                        productVariationVOs.add(defaultPrice(oldProductVO.getPrice()));
                    }
                    if (!StringUtils.isEmpty(oldProductVO.getPriceSmall())) {
                        productVariationVOs.add(smallPrice(oldProductVO.getPriceSmall()));
                    }
                    if (!StringUtils.isEmpty(oldProductVO.getPriceMedium())) {
                        productVariationVOs.add(mediumPrice(oldProductVO.getPriceMedium()));
                    }
                    if (!StringUtils.isEmpty(oldProductVO.getPriceLarge())) {
                        productVariationVOs.add(largePrice(oldProductVO.getPriceLarge()));
                    }
                    productVO.setProductVariations(productVariationVOs);
                    productVOs.add(productVO);
                    productGroupVO.setProducts(productVOs);
                }
                productCategoryVO.setProductGroups(productGroupVOs);
                productCategoryVOs.add(productCategoryVO);
            }
            productCatalogVO.setProductCategories(productCategoryVOs);
            productCatalogVOs.add(productCatalogVO);
        }
        menuVO.setProductCataloges(productCatalogVOs);
        return menuVO;
    }

    private ProductVariationVO largePrice(String price) {
        return productVariation("large", price);
    }

    private ProductVariationVO mediumPrice(String price) {
        return productVariation("medium", price);
    }

    private ProductVariationVO smallPrice(String price) {
        return productVariation("small", price);
    }

    private ProductVariationVO defaultPrice(String price) {
        return productVariation("default", price);
    }

    private ProductVariationVO productVariation(String variation, String price) {
        ProductVariationVO productVariationVO = new ProductVariationVO();
        productVariationVO.setName(variation);
        productVariationVO.setPrice(new BigDecimal(price));
        return productVariationVO;
    }

    @RequestMapping(value = "/legacy", method = RequestMethod.POST)
    public
    @ResponseBody
    OldMenuVO responseFoodMenu(Principal principal, Model model) throws IOException {
        String menu = new String(Files.readAllBytes(Paths.get("documentation/Speisekarte.json")));
        return objectMapper.readValue(menu, OldMenuVO.class);
    }

}
