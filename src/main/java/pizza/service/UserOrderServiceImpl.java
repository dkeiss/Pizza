package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.product.ProductVariation;
import pizza.repositories.*;
import pizza.vo.order.UserOrderVO;

import java.util.List;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderService userOrderService;

    @Autowired
    private BulkOrderService bulkOrderService;

    @Autowired
    private ProductCatalogService productCatalogService;

    @Override
    public List<UserOrderVO> addUserOrder(UserOrderVO userOrderVO) {
        isUserOrderValid(userOrderVO);

        return null;
    }

    private void isUserOrderValid(UserOrderVO userOrderVO) {

    }

    @Override
    public List<UserOrderVO> getUserOrders() {
        return null;
    }

}
