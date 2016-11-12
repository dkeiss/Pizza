package pizza.controller.rest.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.ProductCatalogService;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.io.IOException;
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
    List<ProductCatalogInfoVO> listProductInfos() throws IOException {
        return productCatalogService.listProductCataloges();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    ProductCatalogVO createProductCatalog(@RequestBody ProductCatalogVO productCatalog) throws IOException {
        return productCatalogService.createProductCatalog(productCatalog);
    }

    @RequestMapping(value = "{productCatalogId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ProductCatalogVO getProductCatalog(@PathVariable("productCatalogId") Integer productCatalogId) throws IOException {
        return productCatalogService.getProductCatalog(productCatalogId);
    }

    @RequestMapping(value = "active", method = RequestMethod.GET)
    public
    @ResponseBody
    ProductCatalogVO getActiveProductCatalog() throws IOException {
        return productCatalogService.getActiveProductCatalog();
    }

}
