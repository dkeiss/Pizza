package pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import pizza.vo.product.additional.AdditionalCategoryVO;
import pizza.vo.product.menu.ProductCatalogFullVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 17.11.2016.
 */
public class AdminProductCatalogServiceImplTest {

    @InjectMocks
    private AdminProductCatalogService adminProductCatalogService = new AdminProductCatalogServiceImpl();

    @Mock
    private AdditionalService additionalService;

    @Mock
    private ProductCatalogService productCatalogService;


    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void createProductCatalogFullWithoutAdditionals() throws Exception {
        ProductCatalogFullVO productCatalogFull = new ProductCatalogFullVO();
        productCatalogFull.setProductCatalog(new ProductCatalogVO());
        when(productCatalogService.createProductCatalog(productCatalogFull.getProductCatalog())).thenReturn(new ProductCatalogVO());

        ProductCatalogInfoVO productCatalogInfo = adminProductCatalogService.createProductCatalogFull(productCatalogFull);

        assertNotNull(productCatalogInfo);
    }

    @Test
    public void createProductCatalogFull() throws Exception {
        ProductCatalogFullVO productCatalogFull = new ProductCatalogFullVO();
        productCatalogFull.setAdditionals(Arrays.asList(new AdditionalCategoryVO()));
        productCatalogFull.setProductCatalog(new ProductCatalogVO());
        when(productCatalogService.createProductCatalog(productCatalogFull.getProductCatalog())).thenReturn(new ProductCatalogVO());

        ProductCatalogInfoVO productCatalogInfo = adminProductCatalogService.createProductCatalogFull(productCatalogFull);

        assertNotNull(productCatalogInfo);
    }

}