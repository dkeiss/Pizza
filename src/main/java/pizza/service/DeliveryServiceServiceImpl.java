package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.order.deliveryservice.DeliveryService;
import pizza.repositories.DeliveryServiceRepository;
import pizza.vo.deliveryservice.DeliveryServiceVO;

import java.util.Date;

import static pizza.service.common.ObjectMapperUtil.copyFromBusinessObject;
import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;

/**
 * Created by Daniel Keiss on 11.11.2016.
 */
@Service
public class DeliveryServiceServiceImpl implements DeliveryServiceService {

    @Autowired
    private DeliveryServiceRepository deliveryServiceRepository;

    private final static int DELIVERY_SERVICE_ID = 100;

    @Override
    public DeliveryServiceVO getDeliveryService() {
        DeliveryService deliveryService = getDeliveryServiceBO();
        return copyFromBusinessObject(deliveryService, new DeliveryServiceVO());
    }

    @Override
    public void updateDeliveryService(DeliveryServiceVO deliveryServiceVO) {
        DeliveryService deliveryService = getDeliveryServiceBO();
        copyFromValueObject(deliveryServiceVO, deliveryService);
        deliveryService.setDeliveryServiceId(DELIVERY_SERVICE_ID);
        deliveryService.setCreationDate(new Date());
        deliveryService.setModificationDate(new Date());
        deliveryServiceRepository.save(deliveryService);
    }

    private DeliveryService getDeliveryServiceBO() {
        DeliveryService deliveryService = deliveryServiceRepository.findOne(DELIVERY_SERVICE_ID);
        if (deliveryService == null) {
            deliveryService = new DeliveryService();
            deliveryService.setDeliveryServiceId(DELIVERY_SERVICE_ID);
            deliveryService = deliveryServiceRepository.save(deliveryService);
        }
        return deliveryService;
    }

}