package pizza.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserOrderService;
import pizza.service.exception.NotFoundException;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderPaidVO;
import pizza.vo.order.UserOrderVO;
import pizza.vo.product.additional.AdditionalInfoVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static pizza.controller.ResponseUtil.getResponseWithStatus;

/**
 * Created by Daniel Keiss on 06.11.2016.
 */
@Controller
@RequestMapping("rest/admin/shoppingcard")
public class AdminShoppingcardController {

    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserOrderDetailsVO> getUserOrders() {
        return userOrderService.getUserOrders();
    }

    @RequestMapping(value = "/{userOrderId}", method = RequestMethod.GET)
    public
    @ResponseBody
    UserOrderDetailsVO getUserOrder(@PathVariable("userOrderId") Integer userOrderId) {
        return userOrderService.getUserOrder(userOrderId);
    }

    @RequestMapping(value = "/{userOrderId}/paid", method = RequestMethod.PUT)
    public
    @ResponseBody
    Map setUserOrderPaid(@PathVariable("userOrderId") Integer userOrderId, @RequestBody UserOrderPaidVO userOrderPaidVO) {
        userOrderService.setUserOrderPaid(userOrderId, userOrderPaidVO);
        return getResponseWithStatus(true);
    }

    @RequestMapping(value = "/{userOrderId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Map deleteUserOrder(@PathVariable("userOrderId") Integer userOrderId) {
        userOrderService.deleteUserOrder(userOrderId);
        return getResponseWithStatus(true);
    }

}
