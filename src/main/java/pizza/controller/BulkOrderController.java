package pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.repositories.BulkOrderRepository;
import pizza.service.BulkOrderService;
import pizza.vo.order.OrderActivateVO;
import pizza.vo.order.BulkOrderVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Daniel Keiss on 24.10.2016.
 */
@Controller
@RequestMapping("rest/bulkorder")
public class BulkOrderController {

    @Autowired
    private BulkOrderService bulkOrderService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<BulkOrderVO> listBulkOrders() {
        return bulkOrderService.listBulkOrders();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    BulkOrderVO createBulkOrder(@RequestBody BulkOrderVO bulkOrder) {
        return bulkOrderService.createBulkOrder(bulkOrder);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<BulkOrderVO> getBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId) {
        BulkOrderVO bulkOrderVO = bulkOrderService.getBulkOrderById(bulkorderId);
        if (bulkOrderVO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bulkOrderVO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    void updateBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId, @RequestBody BulkOrderVO bulkOrder) {
        bulkOrder.setBulkOrderId(bulkorderId);
        bulkOrderService.updateBulkOrderById(bulkOrder);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    void deleteBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId) {
        bulkOrderService.deleteBulkOrderById(bulkorderId);
    }

}
