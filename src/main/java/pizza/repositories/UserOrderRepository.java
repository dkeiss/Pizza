package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.UserOrder;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.domain.user.User;

import java.util.List;

/**
 * Created by Daniel Keiss on 08.11.2016.
 */
public interface UserOrderRepository extends CrudRepository<UserOrder, Integer> {

    List<UserOrder> findByUser(User user);


}
