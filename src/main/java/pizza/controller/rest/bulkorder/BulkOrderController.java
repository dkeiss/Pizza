package pizza.controller.rest.bulkorder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.BulkOrderService;
import pizza.vo.order.BulkOrderVO;

import java.util.*;

import static pizza.controller.rest.ResponseUtil.getEmptyJsonSucessResponse;

/**
 * Created by Daniel Keiss on 24.10.2016.
 */
@RestController
@RequestMapping("rest/bulkorder")
public class BulkOrderController {

    @Autowired
    private BulkOrderService bulkOrderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<BulkOrderVO> listBulkOrders() {
        return bulkOrderService.listBulkOrders();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public BulkOrderVO createBulkOrder(@RequestBody BulkOrderVO bulkOrder) {
        return bulkOrderService.createBulkOrder(bulkOrder);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.GET)
    public BulkOrderVO getBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId) {
        return bulkOrderService.getBulkOrderById(bulkorderId);
    }

    @RequestMapping(value = "/active", method = RequestMethod.GET)
    public BulkOrderVO getActiveBulkOrder() {
        return bulkOrderService.getActiveBulkOrder();
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public BulkOrderVO getOpenBulkOrder() {
        return bulkOrderService.getOpenBulkOrder();
    }

    @RequestMapping(value = "/finish", method = RequestMethod.POST)
    public BulkOrderVO finishBulkOrder() {
        return bulkOrderService.finishOpenBulkOrder();
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.PUT)
    public Map updateBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId, @RequestBody BulkOrderVO bulkOrder) {
        bulkOrder.setBulkOrderId(bulkorderId);
        bulkOrderService.updateBulkOrder(bulkOrder);
        return getEmptyJsonSucessResponse();
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.DELETE)
    public Map deactivateBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId) {
        bulkOrderService.deactivateBulkOrderById(bulkorderId);
        return getEmptyJsonSucessResponse();
    }

}
