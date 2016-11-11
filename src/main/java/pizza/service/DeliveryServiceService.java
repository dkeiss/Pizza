package pizza.service;

import pizza.vo.deliveryservice.DeliveryServiceVO;

/**
 * Created by Daniel Keiss on 11.11.2016.
 */
public interface DeliveryServiceService {

	DeliveryServiceVO getDeliveryService();

	void updateDeliveryService(DeliveryServiceVO deliveryService);

}
