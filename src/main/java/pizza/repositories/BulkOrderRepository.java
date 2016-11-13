package pizza.repositories;

import org.springframework.data.repository.CrudRepository;
import pizza.domain.order.BulkOrder;

import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
public interface BulkOrderRepository extends CrudRepository<BulkOrder, Integer> {

    List<BulkOrder> findByActiveUntilGreaterThan(Date activeUntil);

    List<BulkOrder> findByFinished(Boolean finished);

}
