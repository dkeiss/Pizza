package pizza.service;

import pizza.domain.order.BulkOrder;
import pizza.vo.order.BulkOrderVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 26.10.2016.
 */
public interface BulkOrderService {

    List<BulkOrderVO> listBulkOrders();

    BulkOrderVO createBulkOrder(BulkOrderVO bulkOrder);

    BulkOrderVO getBulkOrderById(Integer bulkorderId);

    void deleteBulkOrderById(Integer bulkorderId);

    void updateBulkOrderById(BulkOrderVO bulkOrderVO);

    BulkOrderVO getActiveBulkOrder();

    BulkOrder findActiveBulkOrder();

}
