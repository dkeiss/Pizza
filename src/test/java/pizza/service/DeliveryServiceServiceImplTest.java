package pizza.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import pizza.domain.order.deliveryservice.DeliveryService;
import pizza.repositories.DeliveryServiceRepository;
import pizza.vo.deliveryservice.DeliveryServiceVO;

import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by Daniel Keiss on 19.11.2016.
 */
public class DeliveryServiceServiceImplTest {

    @InjectMocks
    private DeliveryServiceService deliveryServiceService = new DeliveryServiceServiceImpl();

    @Mock
    private DeliveryServiceRepository deliveryServiceRepository;

    @Mock
    private DeliveryService deliveryService;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void getExistingDeliveryService() throws Exception {
        when(deliveryServiceRepository.findOne(100)).thenReturn(deliveryService);

        DeliveryServiceVO deliveryService = deliveryServiceService.getDeliveryService();

        assertNotNull(deliveryService);
        verify(deliveryServiceRepository, never()).save(any(DeliveryService.class));
    }

    @Test
    public void getNotYetAvailableDeliveryService() throws Exception {
        when(deliveryServiceRepository.save(any(DeliveryService.class))).thenReturn(deliveryService);

        DeliveryServiceVO deliveryService = deliveryServiceService.getDeliveryService();

        assertNotNull(deliveryService);
        verify(deliveryServiceRepository).save(any(DeliveryService.class));
    }

    @Test
    public void updateExistingDeliveryService() throws Exception {
        when(deliveryServiceRepository.findOne(100)).thenReturn(deliveryService);

        deliveryServiceService.updateDeliveryService(new DeliveryServiceVO());

        assertNotNull(deliveryService);
        verify(deliveryServiceRepository).save(any(DeliveryService.class));
        verify(deliveryService).setCreationDate(any());
        verify(deliveryService).setModificationDate(any());
    }

    @Test
    public void updateNotYetAvailableDeliveryService() throws Exception {
        when(deliveryServiceRepository.save(any(DeliveryService.class))).thenReturn(deliveryService);
        ArgumentCaptor<DeliveryService> argument = ArgumentCaptor.forClass(DeliveryService.class);
        when(deliveryServiceRepository.save(argument.capture())).thenReturn(deliveryService);

        deliveryServiceService.updateDeliveryService(new DeliveryServiceVO());

        assertNotNull(deliveryService);
        verify(deliveryServiceRepository, times(2)).save(any(DeliveryService.class));
        verify(deliveryService).setCreationDate(any());
        verify(deliveryService).setModificationDate(any());
    }

}