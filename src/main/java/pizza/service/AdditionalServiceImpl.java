package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.repositories.AdditionalCategoryRepository;
import pizza.repositories.AdditionalPriceRepository;
import pizza.repositories.AdditionalRepository;
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

    @Autowired
    private AdditionalRepository additionalRepository;

    @Autowired
    private AdditionalPriceRepository additionalPriceRepository;

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
        AdditionalCategory additionalCategory = findAdditionalCategory(additionalCategoryId);
        if (additionalCategory == null) {
            throw new NotFoundException();
        }
        return getAdditionalCategoryFromBO(additionalCategory);
    }

    private AdditionalCategory findAdditionalCategory(Integer additionalCategoryId) {
        return additionalCategoryRepository.findOne(additionalCategoryId);
    }

    @Override
    public List<AdditionalCategoryVO> getAdditionalsByProductId(Integer productId) {
        List<AdditionalCategoryVO> additionalCategoryVOs = listAdditionalCategories();
        List<AdditionalCategoryVO> selected = additionalCategoryVOs.stream().filter(additionalCategoryVO -> additionalCategoryVO.getProductIds().contains(productId)).collect(Collectors.toList());
        return selected;
    }

    @Override
    public Additional findAdditional(Integer additionalId) {
        return additionalRepository.findOne(additionalId);
    }

    @Override
    public AdditionalPrice findAdditionalPrice(Integer additionalPriceId) {
        return additionalPriceRepository.findOne(additionalPriceId);
    }

}
