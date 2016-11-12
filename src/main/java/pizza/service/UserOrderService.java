package pizza.service;

import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderPaidVO;
import pizza.vo.order.UserOrderVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
public interface UserOrderService {

    UserOrderDetailsVO addUserOrder(UserOrderVO userOrder);

    List<UserOrderDetailsVO> getUserOrders();

    UserOrderDetailsVO getUserOrder(Integer userOrderId);

    void setUserOrderPaid(Integer userOrderId, UserOrderPaidVO userOrderPaidVO);

    void deleteUserOrder(Integer userOrderId);

}
