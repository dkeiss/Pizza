package pizza.controller.rest.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserOrderService;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderPaidVO;

import java.util.List;
import java.util.Map;

import static pizza.controller.rest.ResponseUtil.getEmptyJsonSucessResponse;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@RestController
@RequestMapping("rest/admin/shoppingcard")
public class AdminShoppingcardController {

    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UserOrderDetailsVO> getUserOrders() {
        return userOrderService.getCurrentUserOrdersIncludingDiscount();
    }

    @RequestMapping(value = "/{userOrderId}", method = RequestMethod.GET)
    public UserOrderDetailsVO getUserOrder(@PathVariable("userOrderId") Integer userOrderId) {
        return userOrderService.getUserOrder(userOrderId);
    }

    @RequestMapping(value = "/{userOrderId}/paid", method = RequestMethod.PUT)
    public Map setUserOrderPaid(@PathVariable("userOrderId") Integer userOrderId, @RequestBody UserOrderPaidVO userOrderPaidVO) {
        userOrderService.setUserOrderPaid(userOrderId, userOrderPaidVO);
        return getEmptyJsonSucessResponse();
    }

    @RequestMapping(value = "/{userOrderId}", method = RequestMethod.DELETE)
    public Map deleteUserOrder(@PathVariable("userOrderId") Integer userOrderId) {
        userOrderService.deleteUserOrder(userOrderId);
        return getEmptyJsonSucessResponse();
    }

}
