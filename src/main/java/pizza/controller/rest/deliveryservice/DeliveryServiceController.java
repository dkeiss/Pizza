package pizza.controller.rest.deliveryservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.DeliveryServiceService;
import pizza.vo.deliveryservice.DeliveryServiceVO;

import java.util.Map;

import static pizza.controller.rest.ResponseUtil.getEmptyJsonSucessResponse;

/**
 * Created by Daniel Keiss on 11.11.2016.
 */
@RestController
@RequestMapping("rest/deliveryservice")
public class DeliveryServiceController {

    @Autowired
    private DeliveryServiceService deliveryServiceService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public DeliveryServiceVO getDeliveryService() {
        return deliveryServiceService.getDeliveryService();
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Map updateDeliveryService(@RequestBody DeliveryServiceVO deliveryService) {
        deliveryServiceService.updateDeliveryService(deliveryService);
        return getEmptyJsonSucessResponse();
    }

}
