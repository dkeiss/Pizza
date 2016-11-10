package pizza.service.common;

import pizza.domain.order.deliveryservice.DeliveryService;
import pizza.domain.order.BulkOrder;
import pizza.vo.order.BulkOrderVO;

import java.util.Date;

import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public class BulkOrderValueToBusinessObjectConverter {

    public static BulkOrder createBulkOrderFromVO(BulkOrderVO bulkOrderVO) {
        DeliveryService deliveryServiceBO = copyFromValueObject(bulkOrderVO.getDeliveryService(), new DeliveryService());
        deliveryServiceBO.setCreationDate(new Date());
        BulkOrder bulkOrderBO = copyFromValueObject(bulkOrderVO, new BulkOrder());
        bulkOrderBO.setBulkOrderId(null);
        bulkOrderBO.setDeliveryService(deliveryServiceBO);
        bulkOrderBO.setCreationDate(new Date());
        return bulkOrderBO;
    }

}
