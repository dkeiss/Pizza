package pizza.controller.rest.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pizza.service.AdditionalService;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
@RestController
@RequestMapping("rest/additional")
public class AdditionalController {

    @Autowired
    private AdditionalService additionalService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<AdditionalCategoryVO> listAdditionals() throws IOException {
        return additionalService.listAdditionalCategoriesFromActiveProductCatalog();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public AdditionalCategoryVO createAdditional(@RequestBody AdditionalCategoryVO additionalCategory) throws IOException {
        return additionalService.createAdditionalCategoryForActiveProductCatalog(additionalCategory);
    }

    @RequestMapping(value = "/full", method = RequestMethod.POST)
    public List<AdditionalCategoryVO> createAdditionals(@RequestBody List<AdditionalCategoryVO> additionalCategorys) throws IOException {
        List<AdditionalCategoryVO> additionalCategoryVOs = additionalCategorys.stream().map(additionalCategory -> additionalService.createAdditionalCategoryForActiveProductCatalog(additionalCategory)).collect(Collectors.toList());
        return additionalCategoryVOs;
    }

    @RequestMapping(value = "{additionalCategoryId}", method = RequestMethod.GET)
    public AdditionalCategoryVO getAdditional(@PathVariable("additionalCategoryId") Integer additionalCategoryId) throws IOException {
        return additionalService.getAdditionalCategory(additionalCategoryId);
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public List<AdditionalCategoryVO> getAdditionalByProductId(@PathVariable("productId") Integer productId) throws IOException {
        return additionalService.getAdditionalsByProductIdForActiveProductCatalog(productId);
    }

}
