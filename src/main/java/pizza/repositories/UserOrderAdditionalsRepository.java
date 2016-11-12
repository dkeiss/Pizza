package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.UserOrderAdditional;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public interface UserOrderAdditionalsRepository extends CrudRepository<UserOrderAdditional, Integer> {
}
