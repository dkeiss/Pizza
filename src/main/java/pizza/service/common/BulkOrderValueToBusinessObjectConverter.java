package pizza.service.common;

import pizza.domain.deliveryservice.DeliveryService;
import pizza.domain.order.BulkOrder;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.vo.deliveryservice.DeliveryServiceVO;
import pizza.vo.order.BulkOrderVO;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.util.Date;

import static pizza.service.common.ObjectMapperUtil.copyFromValueObject;

/**
 * Created by Daniel Keiss on 10.11.2016.
 */
public class BulkOrderValueToBusinessObjectConverter {

    public static BulkOrder createBulkOrderFromVO(BulkOrderVO bulkOrderVO) {
        DeliveryService deliveryServiceBO = copyFromValueObject(bulkOrderVO.getDeliveryService(), new DeliveryService());
        BulkOrder bulkOrderBO = copyFromValueObject(bulkOrderVO, new BulkOrder());
        bulkOrderBO.setBulkOrderId(null);
        bulkOrderBO.setDeliveryService(deliveryServiceBO);
        bulkOrderBO.setCreationDate(new Date());
        return bulkOrderBO;
    }

}
