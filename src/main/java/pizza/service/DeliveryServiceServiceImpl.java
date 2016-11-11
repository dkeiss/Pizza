package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.order.deliveryservice.DeliveryService;
import pizza.repositories.DeliveryServiceRepository;
import pizza.vo.deliveryservice.DeliveryServiceVO;

import java.util.Date;

import static pizza.service.common.ObjectMapperUtil.copyFromBusinessObject;

/**
 * Created by Daniel Keiss on 11.11.2016.
 */
@Service
public class DeliveryServiceServiceImpl implements DeliveryServiceService {

	@Autowired
	private DeliveryServiceRepository deliveryServiceRepository;

	@Override
	public DeliveryServiceVO getDeliveryService() {
		DeliveryService deliveryService = getDeliveryServiceBO();
		return copyFromBusinessObject(deliveryService, new DeliveryServiceVO());
	}

	@Override
	public void updateDeliveryService(DeliveryServiceVO deliveryServiceVO) {
		DeliveryService deliveryService = getDeliveryServiceBO();
		deliveryService.setDeliveryServiceName(deliveryServiceVO.getDeliveryServiceName());
		deliveryService.setFirstName(deliveryServiceVO.getFirstName());
		deliveryService.setLastName(deliveryServiceVO.getLastName());
		deliveryService.setPostalCode(deliveryServiceVO.getPostalCode());
		deliveryService.setStreet(deliveryServiceVO.getStreet());
		deliveryService.setTown(deliveryServiceVO.getTown());
		deliveryService.setModificationDate(new Date());
		deliveryServiceRepository.save(new DeliveryService());
	}

	private DeliveryService getDeliveryServiceBO() {
		Iterable<DeliveryService> deliveryServices = deliveryServiceRepository.findAll();
		DeliveryService deliveryService = deliveryServices.iterator().hasNext() ? deliveryServices.iterator().next() : null;
		if (deliveryService == null) {
			deliveryService = deliveryServiceRepository.save(new DeliveryService());
		}
		return deliveryService;
	}

}