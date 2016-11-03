package pizza.controller.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserOrderService;
import pizza.vo.order.UserOrderVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Controller
@RequestMapping("rest/user/")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping(value = "order", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserOrderVO> getUserOrders() {
        return userOrderService.getUserOrders();
    }

    @RequestMapping(value = "order", method = RequestMethod.POST)
    public
    @ResponseBody
    List<UserOrderVO> addUserOrder(@RequestBody UserOrderVO userOrder) {
        return userOrderService.addUserOrder(userOrder);
    }

    @RequestMapping(value = "order/{userId}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Map updateUserOrder(@PathVariable("userId") Integer userId, @RequestBody UserOrderVO userOrder) {
        return new HashMap();
    }

}
