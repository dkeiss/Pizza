package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.deliveryservice.DeliveryService;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public interface DeliveryServiceRepository extends CrudRepository<DeliveryService, Integer> {
}
