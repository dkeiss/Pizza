package pizza.service;

import org.springframework.stereotype.Service;
import pizza.vo.order.UserOrderVO;

import java.util.List;

/**
 * Created by Keissenator on 22.10.2016.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Override
    public List<UserOrderVO> addUserOrder(UserOrderVO userOrder) {
        return null;
    }

    @Override
    public List<UserOrderVO> getUserOrders() {
        return null;
    }

}
