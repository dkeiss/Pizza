package pizza.service.common;

import pizza.domain.order.BulkOrder;
import pizza.vo.deliveryservice.DeliveryServiceVO;
import pizza.vo.order.BulkOrderVO;

import static pizza.service.common.ObjectMapperUtil.copyFromBusinessObject;
import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public class BulkOrderBusinessToValueObjectConverter {

    public static BulkOrderVO getBulkOrderFromBO(BulkOrder bulkOrderBO) {
        BulkOrderVO bulkOrderVO = copyFromBusinessObject(bulkOrderBO, new BulkOrderVO());
        DeliveryServiceVO deliveryServiceVO = copyFromBusinessObject(bulkOrderBO.getDeliveryService(), new DeliveryServiceVO());
        bulkOrderVO.setDeliveryService(deliveryServiceVO);
        return bulkOrderVO;
    }

}
