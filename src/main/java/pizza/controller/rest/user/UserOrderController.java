package pizza.controller.rest.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.UserOrderService;
import pizza.service.exception.user.UserNotLoggedInException;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderVO;

import java.security.Principal;
import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@RestController
@RequestMapping("rest/user")
public class UserOrderController {

    @Autowired
    private UserOrderService userOrderService;

    @RequestMapping(value = "order", method = RequestMethod.POST)
    public UserOrderDetailsVO addUserOrder(@RequestBody UserOrderVO userOrder, Principal principal) {
        if (principal == null) {
            throw new UserNotLoggedInException();
        }
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        return userOrderService.addUserOrder(authenticationToken.getName(), userOrder);
    }

    @RequestMapping(value = "{userId}/order", method = RequestMethod.GET)
    public List<UserOrderDetailsVO> getUserOrders(@PathVariable("userId") Integer userId) {
        return userOrderService.getUserOrders(userId);
    }

}
