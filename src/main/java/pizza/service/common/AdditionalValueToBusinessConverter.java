package pizza.service.common;

import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.additional.AdditionalPriceVO;
import pizza.vo.product.additional.AdditionalVO;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;

/**
 * Created by Daniel Keiss on 30.10.2016.
 */
public class AdditionalValueToBusinessConverter {

    public static AdditionalCategory getAdditionalCategoryFromVO(AdditionalCategoryVO additionalCategoryVO, AdditionalCategory additionalCategory) {
        additionalCategory.setName(additionalCategoryVO.getName());
        additionalCategory.setDuty(additionalCategoryVO.getDuty());
        additionalCategory.setCreationDate(new Date());
        additionalCategory.setAdditionals(getAdditionalsFromVO(additionalCategoryVO.getAdditionals(), additionalCategory));
        additionalCategory.setProductIds(getProductsFromProductsList(additionalCategoryVO.getProductIds()));
        return additionalCategory;
    }

    private static List<Additional> getAdditionalsFromVO(List<AdditionalVO> additionalVOs, AdditionalCategory additionalCategory) {
        return additionalVOs.stream().map(additionalVO -> getAdditionalFromVO(additionalVO, additionalCategory)).collect(Collectors.toList());
    }

    private static Additional getAdditionalFromVO(AdditionalVO additionalVO, AdditionalCategory additionalCategory) {
        Additional additional = new Additional();
        additional.setDescription(additionalVO.getDescription());
        additional.setAdditionalPrices(getAdditionalPricesFromVO(additionalVO.getAdditionalPrices(), additional));
        additional.setAdditionalCategory(additionalCategory);
        additional.setCreationDate(new Date());
        return additional;
    }

    private static List<AdditionalPrice> getAdditionalPricesFromVO(List<AdditionalPriceVO> additionalPriceVOs, Additional additional) {
        return additionalPriceVOs.stream().map(additionalPriceVO -> getAdditionalPriceFromVO(additionalPriceVO, additional)).collect(Collectors.toList());
    }

    private static AdditionalPrice getAdditionalPriceFromVO(AdditionalPriceVO additionalPriceVO, Additional additional) {
        AdditionalPrice additionalPrice = copyFromValueObject(additionalPriceVO, new AdditionalPrice());
        additionalPrice.setCreationDate(new Date());
        additionalPrice.setAdditional(additional);
        return additionalPrice;
    }

    private static String getProductsFromProductsList(List<Integer> productIds) {
        return productIds.stream().map(Object::toString).collect(Collectors.joining(","));
    }

}
