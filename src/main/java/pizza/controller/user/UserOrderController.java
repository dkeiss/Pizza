package pizza.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserOrderService;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderVO;

import java.util.List;
import java.util.Map;

import static pizza.controller.ResponseUtil.getResponseWithStatus;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Controller
@RequestMapping("rest/user")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping(value = "order", method = RequestMethod.POST)
    public
    @ResponseBody
    UserOrderDetailsVO addUserOrder(@RequestBody UserOrderVO userOrder) {
        return userOrderService.addUserOrder(userOrder);
    }

    @RequestMapping(value = "{userId}/order", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserOrderDetailsVO> getUserOrders(@PathVariable("userId") Integer userId) {
        return userOrderService.getUserOrders(userId);
    }

}
