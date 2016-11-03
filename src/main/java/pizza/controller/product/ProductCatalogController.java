package pizza.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pizza.service.ProductCatalogService;
import pizza.vo.product.menu.*;
import pizza.vo.product.menu.oldDELETE.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Daniel Keiss on 28.10.2016.
 */
@Controller
@RequestMapping("rest/productcatalog")
public class ProductCatalogController {

    @Autowired
    private ProductCatalogService productCatalogService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ProductCatalogInfoVO> listProductCataloges() throws IOException {
        return productCatalogService.listProductCataloges();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductCatalogVO createMenu(@RequestBody ProductCatalogVO menuVO) throws IOException {
        return productCatalogService.createProductCatalog(menuVO);
    }

    @RequestMapping(value = "{productCatalogId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ProductCatalogVO getProductCatalog(@PathVariable("productCatalogId") Integer productCatalogId) throws IOException {
        return productCatalogService.getProductCatalog(productCatalogId);
    }

}
