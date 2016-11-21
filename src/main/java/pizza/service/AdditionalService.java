package pizza.service;

import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
public interface AdditionalService {

    List<AdditionalCategoryVO> listAdditionalCategoriesFromActiveProductCatalog();

    AdditionalCategoryVO createAdditionalCategory(Integer productCatalogId, AdditionalCategoryVO additionalCategory);

    AdditionalCategoryVO createAdditionalCategoryForActiveProductCatalog(AdditionalCategoryVO additionalCategory);

    AdditionalCategoryVO getAdditionalCategory(Integer additionalCategoryId);

    List<AdditionalCategoryVO> getAdditionalsByProductIdForActiveProductCatalog(Integer productId);

    Additional findAdditional(Integer additionalId);

    AdditionalPrice findAdditionalPrice(Integer additionalPriceId);

}
