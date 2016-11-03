package pizza.service;

import pizza.vo.product.additional.AdditionalCategoryVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
public interface AdditionalService {

    List<AdditionalCategoryVO> listAdditionalCategories();

    AdditionalCategoryVO createAdditionalCategory(AdditionalCategoryVO additionalCategory);

    AdditionalCategoryVO getAdditionalCategory(Integer additionalCategoryId);

    List<AdditionalCategoryVO> getAdditionalsByProductId(Integer productId);

}
