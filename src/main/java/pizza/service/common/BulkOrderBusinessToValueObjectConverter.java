package pizza.service.common;

import pizza.domain.order.BulkOrder;
import pizza.vo.order.BulkOrderVO;

import static pizza.service.common.ObjectMapperUtil.copyFromBusinessObject;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public class BulkOrderBusinessToValueObjectConverter {

    public static BulkOrderVO getBulkOrderFromBO(BulkOrder bulkOrderBO) {
        BulkOrderVO bulkOrderVO = copyFromBusinessObject(bulkOrderBO, new BulkOrderVO());
        return bulkOrderVO;
    }

}
