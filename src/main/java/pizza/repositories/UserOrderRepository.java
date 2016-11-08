package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.UserOrder;
import pizza.domain.product.additional.AdditionalCategory;

/**
 * Created by Daniel Keiss on 08.11.2016.
 */
public interface UserOrderRepository extends CrudRepository<UserOrder, Integer> {
}
