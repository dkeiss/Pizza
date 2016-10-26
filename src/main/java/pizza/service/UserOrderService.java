package pizza.service;

import pizza.vo.order.UserOrderVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
public interface UserOrderService {

    List<UserOrderVO> addUserOrder(UserOrderVO userOrder);

    List<UserOrderVO> getUserOrders();

}
