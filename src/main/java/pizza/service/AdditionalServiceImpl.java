package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.repositories.AdditionalCategoryRepository;
import pizza.service.exception.NotFoundException;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pizza.service.common.AdditionalBusinessToValueConverter.getAdditionalCategoryFromBO;
import static pizza.service.common.AdditionalBusinessToValueConverter.getAdditionalCategorysFromBO;
import static pizza.service.common.AdditionalValueToBusinessConverter.getAdditionalCategoryFromVO;

/**
 * Created by Daniel Keiss on 03.11.2016.
 */
@Service
public class AdditionalServiceImpl implements AdditionalService {

    @Autowired
    private AdditionalCategoryRepository additionalCategoryRepository;

    @Override
    public List<AdditionalCategoryVO> listAdditionalCategories() {
        List<AdditionalCategory> additionalCategories = new ArrayList<>();
        additionalCategoryRepository.findAll().forEach(additionalCategories::add);
        return getAdditionalCategorysFromBO(additionalCategories);
    }

    @Override
    public AdditionalCategoryVO createAdditionalCategory(AdditionalCategoryVO additionalCategoryVO) {
        AdditionalCategory additionalCategory = getAdditionalCategoryFromVO(additionalCategoryVO, new AdditionalCategory());
        additionalCategory.setCreationDate(new Date());

        additionalCategory = additionalCategoryRepository.save(additionalCategory);

        return getAdditionalCategoryFromBO(additionalCategory);
    }

    @Override
    public AdditionalCategoryVO getAdditionalCategory(Integer additionalCategoryId) {
        AdditionalCategory additionalCategory = additionalCategoryRepository.findOne(additionalCategoryId);
        if (additionalCategory == null) {
            throw new NotFoundException();
        }
        return getAdditionalCategoryFromBO(additionalCategory);
    }

    @Override
    public List<AdditionalCategoryVO> getAdditionalsByProductId(Integer productId) {
        List<AdditionalCategoryVO> additionalCategoryVOs = listAdditionalCategories();
        List<AdditionalCategoryVO> selected = new ArrayList<>();
        for (AdditionalCategoryVO additionalCategoryVO : additionalCategoryVOs) {
            if (additionalCategoryVO.getProductIds().contains(productId)) {
                selected.add(additionalCategoryVO);
            }
        }
        return selected;
    }

}
