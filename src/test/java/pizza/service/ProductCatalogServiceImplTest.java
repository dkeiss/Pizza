package pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import pizza.domain.order.BulkOrder;
import pizza.domain.product.Product;
import pizza.domain.product.ProductCatalog;
import pizza.domain.product.ProductVariation;
import pizza.repositories.ProductCatalogRepository;
import pizza.repositories.ProductRepository;
import pizza.repositories.ProductVariationRepository;
import pizza.service.exception.NotFoundException;
import pizza.vo.order.BulkOrderVO;
import pizza.vo.product.menu.ProductCatalogInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 22.11.2016.
 */
public class ProductCatalogServiceImplTest {

    @InjectMocks
    private ProductCatalogService productCatalogService = new ProductCatalogServiceImpl();

    @Mock
    private BulkOrderService bulkOrderService;

    @Mock
    private ProductCatalogRepository productCatalogRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductVariationRepository productVariationRepository;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void createProductCatalog() throws Exception {
        when(productCatalogRepository.save(any(ProductCatalog.class))).thenReturn(mock(ProductCatalog.class));

        ProductCatalogVO productCatalog = productCatalogService.createProductCatalog(mock(ProductCatalogVO.class));

        assertNotNull(productCatalog);
    }

    @Test
    public void getProductCatalog() throws Exception {
        when(productCatalogRepository.findOne(anyInt())).thenReturn(mock(ProductCatalog.class));

        ProductCatalogVO productCatalog = productCatalogService.getProductCatalog(anyInt());

        assertNotNull(productCatalog);
    }

    @Test(expected = NotFoundException.class)
    public void getProductCatalogNotFound() throws Exception {
        productCatalogService.getProductCatalog(anyInt());
    }

    @Test
    public void getProductCatalogBO() throws Exception {
        when(productCatalogRepository.findOne(anyInt())).thenReturn(mock(ProductCatalog.class));

        ProductCatalog productCatalog = productCatalogService.getProductCatalogBO(anyInt());

        assertNotNull(productCatalog);
    }

    @Test(expected = NotFoundException.class)
    public void getProductCatalogBONotFound() throws Exception {
        productCatalogService.getProductCatalogBO(anyInt());
    }

    @Test
    public void listProductCataloges() throws Exception {
        when(productCatalogRepository.findAll()).thenReturn(singletonList(mock(ProductCatalog.class)));

        List<ProductCatalogInfoVO> productCatalogInfoVOs = productCatalogService.listProductCataloges();

        assertThat(productCatalogInfoVOs.size(), is(1));
    }

    @Test
    public void productCatalogExists() throws Exception {
        when(productCatalogRepository.exists(anyInt())).thenReturn(true);

        boolean productCatalogExists = productCatalogService.productCatalogExists(anyInt());

        assertTrue(productCatalogExists);
    }

    @Test
    public void getActiveProductCatalog() throws Exception {
        BulkOrderVO bulkOrderVO = mock(BulkOrderVO.class);
        when(bulkOrderService.getActiveBulkOrder()).thenReturn(bulkOrderVO);
        when(productCatalogRepository.findOne(anyInt())).thenReturn(mock(ProductCatalog.class));

        ProductCatalogVO activeProductCatalog = productCatalogService.getActiveProductCatalog();

        assertNotNull(activeProductCatalog);
    }

    @Test
    public void getActiveProductCatalogBO() throws Exception {
        BulkOrderVO bulkOrderVO = mock(BulkOrderVO.class);
        when(bulkOrderService.getActiveBulkOrder()).thenReturn(bulkOrderVO);
        when(productCatalogRepository.findOne(anyInt())).thenReturn(mock(ProductCatalog.class));

        ProductCatalog activeProductCatalog = productCatalogService.getActiveProductCatalogBO();

        assertNotNull(activeProductCatalog);
    }

    @Test
    public void productExists() throws Exception {
        when(productRepository.exists(anyInt())).thenReturn(true);

        boolean productExists = productCatalogService.productExists(anyInt());

        assertTrue(productExists);
    }

    @Test
    public void productVariationExists() throws Exception {
        when(productVariationRepository.exists(anyInt())).thenReturn(true);

        boolean productExists = productCatalogService.productVariationExists(anyInt());

        assertTrue(productExists);
    }

    @Test
    public void findProduct() throws Exception {
        when(productRepository.findOne(anyInt())).thenReturn(mock(Product.class));

        Product product = productCatalogService.findProduct(anyInt());

        assertNotNull(product);
    }

    @Test
    public void findProductVariation() throws Exception {
        when(productVariationRepository.findOne(anyInt())).thenReturn(mock(ProductVariation.class));

        ProductVariation productVariation = productCatalogService.findProductVariation(anyInt());

        assertNotNull(productVariation);
    }

}