package pizza.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    private List<BulkOrderVO> bulkOrders = new ArrayList<>();

    private BulkOrderVO currentBulkOrder;

    public BulkOrderController() {
        currentBulkOrder = new BulkOrderVO();
        currentBulkOrder.setBulkOrderId(1);
        currentBulkOrder.setActiveUntil(new Date(new Date().getTime() + 1000 * 60 * 60 * 3));
        currentBulkOrder.setCatalogId(1);
        currentBulkOrder.setName("Current Order");
        bulkOrders.add(currentBulkOrder);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public
    @ResponseBody
    List<BulkOrderVO> listBulkOrders() {
        return bulkOrders;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public
    @ResponseBody
    ResponseEntity<BulkOrderVO> createBulkOrder(@RequestBody BulkOrderVO bulkOrder) {
        bulkOrder.setBulkOrderId(bulkOrders.size() + 1);
        bulkOrders.add(bulkOrder);
        return new ResponseEntity<>(bulkOrder, HttpStatus.OK);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.GET)
    public
    @ResponseBody
    ResponseEntity<BulkOrderVO> getBulkOrder(@RequestParam Integer bulkorderId) {
        BulkOrderVO bulkOrderVO = getBulkOrderById(bulkorderId);
        if (bulkOrderVO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bulkOrderVO, HttpStatus.OK);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    ResponseEntity<Void> updateBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId, @RequestBody BulkOrderVO bulkOrder) {
        BulkOrderVO bulkOrderVO = getBulkOrderById(bulkorderId);
        if (bulkOrderVO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{bulkorderId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    ResponseEntity<Void> deleteBulkOrder(@PathVariable("bulkorderId") Integer bulkorderId) {
        BulkOrderVO bulkOrderVO = getBulkOrderById(bulkorderId);
        if (bulkOrderVO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private BulkOrderVO getBulkOrderById(Integer bulkorderId){
        for (BulkOrderVO bulkOrder : bulkOrders) {
            if(bulkOrder.getBulkOrderId().equals(bulkorderId)){
                return bulkOrder;
            }
        }
        return null;
    }

}
