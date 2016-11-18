package pizza.service.common;

import org.springframework.util.StringUtils;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.additional.AdditionalPriceVO;
import pizza.vo.product.additional.AdditionalVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static pizza.service.common.ObjectMapperUtil.copyFromBusinessObject;

/**
 * Created by Daniel Keiss on 30.10.2016.
 */
public class AdditionalBusinessToValueConverter {

    public static List<AdditionalCategoryVO> getAdditionalCategorysFromBO(List<AdditionalCategory> additionalCategories) {
        return additionalCategories.stream().map(AdditionalBusinessToValueConverter::getAdditionalCategoryFromBO).collect(Collectors.toList());
    }

    public static AdditionalCategoryVO getAdditionalCategoryFromBO(AdditionalCategory additionalCategory) {
        AdditionalCategoryVO additionalCategoryVO = new AdditionalCategoryVO();
        additionalCategoryVO.setAdditionalCategoryId(additionalCategory.getAdditionalCategoryId());
        additionalCategoryVO.setName(additionalCategory.getName());
        additionalCategoryVO.setDuty(additionalCategory.getDuty());
        additionalCategoryVO.setCreationDate(additionalCategoryVO.getCreationDate());
        additionalCategoryVO.setAdditionals(getAdditionalsFromBO(additionalCategory.getAdditionals()));
        additionalCategoryVO.setProductIds(getProductsFromProductsString(additionalCategory));
        return additionalCategoryVO;
    }

    private static List<AdditionalVO> getAdditionalsFromBO(List<Additional> additionals) {
        if(additionals == null){
            return null;
        }
        return additionals.stream().map(AdditionalBusinessToValueConverter::getAdditionalFromBO).collect(Collectors.toList());
    }

    private static AdditionalVO getAdditionalFromBO(Additional additional) {
        AdditionalVO additionalVO = new AdditionalVO();
        additionalVO.setAdditionalId(additional.getAdditionalId());
        additionalVO.setDescription(additional.getDescription());
        additionalVO.setAdditionalPrices(getAdditionalPricesFromBO(additional.getAdditionalPrices()));
        additionalVO.setCreationDate(additional.getCreationDate());
        return additionalVO;
    }

    private static List<AdditionalPriceVO> getAdditionalPricesFromBO(List<AdditionalPrice> additionalPrices) {
        return additionalPrices.stream().map(AdditionalBusinessToValueConverter::getAdditionalPriceFromBO).collect(Collectors.toList());
    }

    private static AdditionalPriceVO getAdditionalPriceFromBO(AdditionalPrice additionalPrice) {
        return copyFromBusinessObject(additionalPrice, new AdditionalPriceVO());
    }

    private static List<Integer> getProductsFromProductsString(AdditionalCategory additionalCategory) {
        String products = additionalCategory.getProductIds();
        if (StringUtils.isEmpty(products)) {
            return new ArrayList<>();
        }
        return Stream.of(products.split("\\s*,\\s*")).mapToInt(Integer::new).boxed().collect(Collectors.toList());
    }

}
