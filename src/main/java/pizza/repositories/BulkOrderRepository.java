package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.BulkOrder;
import pizza.domain.Product;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
public interface BulkOrderRepository extends CrudRepository<BulkOrder, Integer> {
}
