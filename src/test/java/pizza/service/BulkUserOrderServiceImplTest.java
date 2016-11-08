package pizza.service;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pizza.domain.order.BulkOrder;
import pizza.repositories.BulkOrderRepository;
import pizza.service.exception.BulkOrderActiveUntilNotValidException;
import pizza.service.exception.BulkOrderAlreadyActiveException;
import pizza.vo.order.BulkOrderVO;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 27.10.2016.
 */
public class BulkUserOrderServiceImplTest {

    @InjectMocks
    private BulkOrderService bulkOrderService = new BulkOrderServiceImpl();

    @Mock
    private BulkOrderRepository bulkOrderRepository;

    @Mock
    private MailService mailService;

    @Before
    public void before() {
        initMocks(this);
    }

    @Test
    public void listBulkOrders() throws Exception {
        when(bulkOrderRepository.findAll()).thenReturn(Collections.singletonList(mock(BulkOrder.class)));

        List<BulkOrderVO> bulkOrderVOs = bulkOrderService.listBulkOrders();

        assertThat(bulkOrderVOs.size(), is(1));
    }

    @Test
    @Ignore
    public void createValidBulkOrder() throws Exception {
        BulkOrderVO bulkOrder = mock(BulkOrderVO.class);
        when(bulkOrder.isActive()).thenReturn(true);

        bulkOrder = bulkOrderService.createBulkOrder(bulkOrder);

        assertNotNull(bulkOrder);
    }

    @Test(expected = BulkOrderActiveUntilNotValidException.class)
    public void createBulkOrderWithInvalidActiveUntil() throws Exception {
        BulkOrderVO bulkOrder = mock(BulkOrderVO.class);
        when(bulkOrder.isActive()).thenReturn(false);

        bulkOrderService.createBulkOrder(bulkOrder);
    }

    @Test(expected = BulkOrderAlreadyActiveException.class)
    public void createBulkOrderWithAnotherActiveBulkOrder() throws Exception {
        BulkOrderVO bulkOrder = mock(BulkOrderVO.class);
        when(bulkOrder.isActive()).thenReturn(true);
        when(bulkOrderRepository.findByActiveUntilGreaterThan(any())).thenReturn(Collections.singletonList(mock(BulkOrder.class)));

        bulkOrderService.createBulkOrder(bulkOrder);
    }

}