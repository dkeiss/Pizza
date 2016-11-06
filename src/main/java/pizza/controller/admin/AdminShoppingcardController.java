package pizza.controller.admin;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pizza.service.exception.NotFoundException;
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

    private List<UserOrderVO> userOrders = mockUserOrders();

    private List<UserOrderVO> mockUserOrders() {
        List<UserOrderVO> userOrders = new ArrayList<>();
        userOrders.add(getUserOrder(1, 4, "Axel", "Schweiß", 1, "Salat", 1, "Ohne Ei", 1, "Normal", "5.99", false));
        userOrders.add(getUserOrder(2, 17, "Chris", "P. Bacon", 2, "Pizza Schinken", null, null, 2, "Groß", "9.50", false));
        userWithTwoAdditionals(userOrders);
        userOrders.add(getUserOrder(4, 12, "Peter", "Sile", 4, "Nudeln", null, null, 1, "Normal", "12.00", false));
        userOrders.add(getUserOrder(5, 5, "Rosa", "Schlüpfer", 5, "Calzone", null, null, 1, "Normal", "6.50", false));
        userOrders.add(getUserOrder(6, 3, "Wilma", "Bierholen", 6, "Pizza Diavolo", 42, "Jalapenos", 3, "Klein", "7.53", false));
        return userOrders;
    }

    private void userWithTwoAdditionals(List<UserOrderVO> userOrders) {
        UserOrderVO userOrder = getUserOrder(3, 1, "Max", "Mustermann", 3, "Pizza Salami", 2, "Peperonie", 2, "Groß", "7.53", false);
        AdditionalInfoVO additionalInfo = new AdditionalInfoVO();
        additionalInfo.setAdditionalId(4);
        additionalInfo.setDescription("Kapern");
        userOrder.getAdditionals().add(additionalInfo);
        userOrders.add(userOrder);
    }

    private UserOrderVO getUserOrder(Integer orderId, Integer userId, String firstname, String lastname, Integer productId, String productName, Integer additionalId, String additionalName, Integer productVariationId, String productVariationName, String sum, boolean paid) {
        UserOrderVO userOrder = new UserOrderVO();
        userOrder.setUserOrderId(orderId);
        userOrder.setUserId(userId);
        userOrder.setFirstName(firstname);
        userOrder.setLastName(lastname);
        userOrder.setProductId(productId);
        userOrder.setProductName(productName);
        userOrder.setProductVariationId(productVariationId);
        userOrder.setProductVariationName(productVariationName);
        if (additionalId != null) {
            ArrayList<AdditionalInfoVO> additionals = new ArrayList<>();
            AdditionalInfoVO additionalInfo = new AdditionalInfoVO();
            additionalInfo.setAdditionalId(additionalId);
            additionalInfo.setDescription(additionalName);
            additionals.add(additionalInfo);
            userOrder.setAdditionals(additionals);
        }
        userOrder.setSum(new BigDecimal(sum));
        userOrder.setPaid(paid);
        return userOrder;
    }

    @RequestMapping(method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserOrderVO> getUserOrders() {
        return userOrders;
    }

    @RequestMapping(value = "/{userOrderId}", method = RequestMethod.GET)
    public
    @ResponseBody
    UserOrderVO getUserOrder(@PathVariable("userOrderId") Integer userOrderId) {
        return getUserOrderById(userOrderId);
    }

    @RequestMapping(value = "/{userOrderId}/paid", method = RequestMethod.PUT)
    public
    @ResponseBody
    Map setUserOrderPaid(@PathVariable("userOrderId") Integer userOrderId, @RequestBody UserOrderPaidVO userOrderPaidVO) {
        UserOrderVO userOrder = getUserOrderById(userOrderId);
        userOrder.setPaid(userOrderPaidVO.getPaid());
        return getResponseWithStatus(true);
    }

    @RequestMapping(value = "/{userOrderId}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    Map deleteUserOrder(@PathVariable("userOrderId") Integer userOrderId) {
        UserOrderVO userOrder = getUserOrderById(userOrderId);
        userOrders.remove(userOrder);
        return getResponseWithStatus(true);
    }

    private UserOrderVO getUserOrderById(Integer userOrderId) {
        for (UserOrderVO userOrder : userOrders) {
            if (userOrder.getUserOrderId().equals(userOrderId)) {
                return userOrder;
            }
        }
        throw new NotFoundException();
    }

}
