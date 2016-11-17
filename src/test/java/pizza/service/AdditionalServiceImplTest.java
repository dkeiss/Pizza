package pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.repositories.AdditionalCategoryRepository;
import pizza.repositories.AdditionalPriceRepository;
import pizza.repositories.AdditionalRepository;
import pizza.service.exception.NotFoundException;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 17.11.2016.
 */
public class AdditionalServiceImplTest {

    @InjectMocks
    private AdditionalService additionalService = new AdditionalServiceImpl();

    @Mock
    private AdditionalCategoryRepository additionalCategoryRepository;

    @Mock
    private AdditionalRepository additionalRepository;

    @Mock
    private AdditionalPriceRepository additionalPriceRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void listAdditionalCategories() throws Exception {
        AdditionalCategory additionalCategory = new AdditionalCategory();
        when(additionalCategoryRepository.findAll()).thenReturn(Collections.singletonList(additionalCategory));

        List<AdditionalCategoryVO> additionalCategoryVOs = additionalService.listAdditionalCategories();

        assertThat(additionalCategoryVOs.size(), is(1));
    }

    @Test
    public void createAdditionalCategory() throws Exception {
        when(additionalCategoryRepository.save(any(AdditionalCategory.class))).thenReturn(new AdditionalCategory());

        AdditionalCategoryVO additionalCategory = additionalService.createAdditionalCategory(new AdditionalCategoryVO());

        assertNotNull(additionalCategory);
    }

    @Test
    public void getAdditionalCategory() throws Exception {
        when(additionalCategoryRepository.findOne(any())).thenReturn(new AdditionalCategory());

        AdditionalCategoryVO additionalCategory = additionalService.getAdditionalCategory(1);

        assertNotNull(additionalCategory);
    }

    @Test(expected = NotFoundException.class)
    public void getAdditionalCategoryNotFound() throws Exception {
        when(additionalCategoryRepository.findOne(any())).thenReturn(null);

        additionalService.getAdditionalCategory(1);
    }

    @Test
    public void getAdditionalsByProductId() throws Exception {
        AdditionalCategory additionalCategory1 = new AdditionalCategory();
        additionalCategory1.setProductIds("1");
        AdditionalCategory additionalCategory2 = new AdditionalCategory();
        additionalCategory2.setProductIds("2,1");
        when(additionalCategoryRepository.findAll()).thenReturn(Arrays.asList(additionalCategory1, additionalCategory2));

        List<AdditionalCategoryVO> additionalsByProductId = additionalService.getAdditionalsByProductId(1);

        assertThat(additionalsByProductId.size(), is(2));
    }

    @Test
    public void findAdditional() throws Exception {
        when(additionalRepository.findOne(any())).thenReturn(new Additional());

        Additional additional = additionalService.findAdditional(1);

        assertNotNull(additional);
    }

    @Test
    public void findAdditionalNull() throws Exception {
        Additional additional = additionalService.findAdditional(1);

        assertNull(additional);
    }

    @Test
    public void findAdditionalPrice() throws Exception {
        when(additionalPriceRepository.findOne(any())).thenReturn(new AdditionalPrice());

        AdditionalPrice additionalPrice = additionalService.findAdditionalPrice(1);

        assertNotNull(additionalPrice);
    }

    @Test
    public void findAdditionalPriceNull() throws Exception {
        AdditionalPrice additionalPrice = additionalService.findAdditionalPrice(1);

        assertNull(additionalPrice);
    }

}