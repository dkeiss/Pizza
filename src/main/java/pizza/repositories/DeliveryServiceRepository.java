package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.deliveryservice.DeliveryService;
import pizza.domain.product.ProductCatalog;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public interface DeliveryServiceRepository extends CrudRepository<DeliveryService, Integer> {
}
