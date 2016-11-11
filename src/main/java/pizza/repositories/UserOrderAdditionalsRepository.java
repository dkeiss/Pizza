package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.UserOrder;
import pizza.domain.order.UserOrderAdditionals;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public interface UserOrderAdditionalsRepository extends CrudRepository<UserOrderAdditionals, Integer> {
}
