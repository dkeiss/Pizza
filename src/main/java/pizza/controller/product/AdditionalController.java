package pizza.controller.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.AdditionalService;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
@Controller
@RequestMapping("rest/additional")
public class AdditionalController {

    @Autowired
    private AdditionalService additionalService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AdditionalCategoryVO> listAdditionals() throws IOException {
//        String additionalMenuJson = new String(Files.readAllBytes(Paths.get("documentation/ZusatzMenu.json")));
//        AdditionalMenusVO additionalMenusVO = new ObjectMapper().readValue(additionalMenuJson, AdditionalMenusVO.class);
//
//        List<AdditionalCategoryVO> additionalCategorys = new ArrayList<>();
//        for (AdditionalMenuVO additionalMenuVO : additionalMenusVO.getAdditionalMenus()) {
//            for (AdditionMenuAsVO additionMenuAsVO : additionalMenuVO.getAs()) {
//                AdditionalCategoryVO additionalCategoryVO = new AdditionalCategoryVO();
//                additionalCategoryVO.setProductIds(additionalMenuVO.getNumbers());
//                additionalCategoryVO.setDuty(additionMenuAsVO.isDuty());
//                List<AdditionalVO> additionalVOs = new ArrayList<>();
//                for (AdditionalMenuBsVO additionalMenuBsVO : additionMenuAsVO.getBs()) {
//                    AdditionalVO additionalVO = new AdditionalVO();
//                    additionalVO.setDescription(additionalMenuBsVO.getName());
//                    List<AdditionalPriceVO> additionalPriceVOs = new ArrayList<>();
//                    for (BigDecimal price : additionalMenuBsVO.getPrices()) {
//                        AdditionalPriceVO additionalPriceVO = new AdditionalPriceVO();
//                        additionalPriceVO.setPrice(price);
//                        additionalPriceVOs.add(additionalPriceVO);
//                    }
//                    additionalVO.setAdditionalPrices(additionalPriceVOs);
//                    additionalVOs.add(additionalVO);
//                }
//                additionalCategoryVO.setAdditionals(additionalVOs);
//                additionalCategorys.add(additionalCategoryVO);
//            }
//        }
//
//        return additionalCategorys;
        return additionalService.listAdditionalCategories();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    AdditionalCategoryVO createAdditional(@RequestBody AdditionalCategoryVO additionalCategory) throws IOException {
        return additionalService.createAdditionalCategory(additionalCategory);
    }

    @RequestMapping(value = "/full", method = RequestMethod.POST)
    public
    @ResponseBody
    List<AdditionalCategoryVO> createAdditionals(@RequestBody List<AdditionalCategoryVO> additionalCategorys) throws IOException {
        List<AdditionalCategoryVO> additionalCategoryVOs = additionalCategorys.stream().map(additionalCategory -> additionalService.createAdditionalCategory(additionalCategory)).collect(Collectors.toList());
        return additionalCategoryVOs;
    }

    @RequestMapping(value = "{additionalCategoryId}", method = RequestMethod.GET)
    public
    @ResponseBody
    AdditionalCategoryVO getAdditional(@PathVariable("additionalCategoryId") Integer additionalCategoryId) throws IOException {
        return additionalService.getAdditionalCategory(additionalCategoryId);
    }

    @RequestMapping(value = "/product/{productId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AdditionalCategoryVO> getAdditionalByProductId(@PathVariable("productId") Integer productId) throws IOException {
        return additionalService.getAdditionalsByProductId(productId);
    }

}