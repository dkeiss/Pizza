package pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pizza.vo.product.menu.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Controller
@RequestMapping("rest/menu")
public class MenuController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    MenuVO getMenu(Principal principal, Model model) throws IOException {
        MenuVO menuVO = new MenuVO();
        ProductCatalogVO angebot = new ProductCatalogVO();
        angebot.setName("Angebot");
        ProductCategoryVO indischeGerichteCategory = new ProductCategoryVO();
        indischeGerichteCategory.setName("Indische Gerichte");
        ProductGroupVO indischeGerichteGroup = new ProductGroupVO();
        indischeGerichteGroup.setName("Indische Gerichte");
        indischeGerichteGroup.setProducts(Arrays.asList(
                createProductWithOnePrice(730, "Murga Mandel-Curry", "Hähnchenfleisch mit Mandeln, versch. Gemüse, leicht-scharfer Currysauce", "7.50"),
                createProductWithOnePrice(731, "Aw Saag", "Kartoffeln, Broccoli, fr. Paprika, fr. Tomaten, Spinat, leicht-scharfe Currysauce", "7.20"),
                createProductWithOnePrice(732, "Lamm Vidolor", "Lammfleisch gebraten, fr. Tomaten, fr. Paprika, Ingwer, Zwiebeln, leicht-scharfe Currysauce", "9.60"),
                createProductWithOnePrice(733, "Lamm Bhindi", "Lammfleisch mit fr. Tomaten, Auberginen, Zucchini, Zwiebeln, leicht-scharfe Currysauce", "9.60"),
                createProductWithOnePrice(734, "Murgh Sabji", "Hühnerfleisch mit Gemüse in deftig-leckerer Currysauce", "7.90"),
                createProductWithOnePrice(735, "Murgh Vindaloo", "Hühnerfleisch mit Kartoffeln in deftig-leckerer Currysauce", "7.90"),
                createProductWithOnePrice(736, "Lamm Saag", "Lammfleisch gebraten mit Ingwer, Knoblauch, Spinat, fr. Tomaten, Currysauce", "9.60"),
                createProductWithOnePrice(737, "Karahi Chicken", "Hähnchenfleisch, fr. Paprika, Zwiebeln, Peperoni, Ingwer, Currysauce", "7.80"),
                createProductWithOnePrice(738, "Prawn Khumbi", "Garnelen, fr. Tomaten, Zwiebeln, fr. Champignons in Currysauce", "11.90")
        ));
        indischeGerichteCategory.setProductGroups(Arrays.asList(indischeGerichteGroup));
        angebot.setProductCategories(Arrays.asList(indischeGerichteCategory));
        menuVO.setProductCataloges(Arrays.asList(angebot));
        return menuVO;
    }

    private ProductVO createProductWithOnePrice(int number, String name, String description, String price) {
        ProductVO productVO = new ProductVO();
        productVO.setNumber(number);
        productVO.setName(name);
        productVO.setDescription(description);
        ProductVariationVO productVariationVO = new ProductVariationVO();
        productVariationVO.setName("Standard");
        productVariationVO.setPrice(new BigDecimal(price));
        productVO.setProductVariations(Collections.singletonList(productVariationVO));
        return productVO;
    }

}
