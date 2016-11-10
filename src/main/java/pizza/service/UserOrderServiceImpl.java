package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.domain.order.UserOrder;
import pizza.domain.order.UserOrderAdditionals;
import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;
import pizza.domain.product.additional.AdditionalCategory;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.domain.user.User;
import pizza.repositories.*;
import pizza.service.exception.*;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderVO;
import pizza.vo.product.additional.AdditionalCategoryVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static pizza.service.common.UserOrderBusinessToValueObjectConverter.getUserOrderFromBO;
import static pizza.service.common.UserOrderBusinessToValueObjectConverter.getUserOrdersFromBOs;

/**
 * Created by Daniel Keiss on 22.10.2016.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    @Autowired
    private UserOrderRepository userOrderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BulkOrderService bulkOrderService;

    @Autowired
    private ProductCatalogService productCatalogService;

    @Autowired
    private AdditionalService additionalService;

    @Override
    public UserOrderDetailsVO addUserOrder(UserOrderVO userOrderVO) {
        checkUserOrderValid(userOrderVO);
        BulkOrder activeBulkOrder = bulkOrderService.findActiveBulkOrder();
        if (activeBulkOrder == null) {
            throw new UserOrderNoBulkOrderActiveException();
        }

        User user = userService.findUser(userOrderVO.getUserId());
        Product product = productCatalogService.findProduct(userOrderVO.getProductId());
        ProductVariation productVariation = productCatalogService.findProductVariation(userOrderVO.getProductVariationId());
        if (!product.getProductId().equals(productVariation.getProduct().getProductId())) {
            throw new UserOrderProductAndProductVariationNotMatchException();
        }
        List<UserOrderAdditionals> userOrderAdditionals = createUserOrderAdditionals(userOrderVO);

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setBulkOrder(activeBulkOrder);
        userOrder.setProduct(product);
        userOrder.setProductVariation(productVariation);
        userOrder.setUserOrderAdditionals(userOrderAdditionals);
        userOrder.setAmount(calculateAmount(productVariation, userOrderAdditionals));
        userOrder.setPaid(false);
        userOrder.setCreationDate(new Date());

        userOrderRepository.save(userOrder);

        return getUserOrderFromBO(userOrder);
    }

    private BigDecimal calculateAmount(ProductVariation productVariation, List<UserOrderAdditionals> userOrderAdditionalses) {
        BigDecimal amount = productVariation.getPrice();
        if (userOrderAdditionalses != null) {
            for (UserOrderAdditionals userOrderAdditional : userOrderAdditionalses) {
                AdditionalPrice additionalPrice = userOrderAdditional.getAdditionalPrice();
                if (additionalPrice == null) {
                    continue;
                }
                amount = amount.add(additionalPrice.getPrice());
            }
        }
        return amount;
    }

    private List<UserOrderAdditionals> createUserOrderAdditionals(UserOrderVO userOrderVO) {
        if (userOrderVO.getAdditionalIds() == null || userOrderVO.getAdditionalIds().isEmpty()) {
            return null;
        }
        List<UserOrderAdditionals> userOrderAdditions = new ArrayList<>();
        for (Integer additionalId : userOrderVO.getAdditionalIds()) {
            // TODO
//            AdditionalCategory additionalCategory = additionalService.findAdditionalCategory(additionalId);
//            if(additionalCategory == null){
//                throw new UserOrderAdditionalNotFoundException();
//            }
//            UserOrderAdditionals userOrderAdditionals = new UserOrderAdditionals();
//            userOrderAdditionals.setAdditional(additionalCategory.);
        }
        return userOrderAdditions;
    }

    private void checkUserOrderValid(UserOrderVO userOrderVO) {
        checkIfUserExist(userOrderVO.getUserId());
        checkIfProductExist(userOrderVO.getProductId());
        checkIfProductVariationExist(userOrderVO.getProductVariationId());
    }

    private void checkIfUserExist(Integer userId) {
        if (!userService.userExists(userId)) {
            throw new UserOrderUserNotFoundException();
        }
    }

    private void checkIfProductExist(Integer productId) {
        if (!productCatalogService.productExists(productId)) {
            throw new UserOrderProductNotFoundException();
        }
    }

    private void checkIfProductVariationExist(Integer productVariationId) {
        if (!productCatalogService.productVariationExists(productVariationId)) {
            throw new UserOrderProductVariationNotFoundException();
        }
    }

    @Override
    public List<UserOrderDetailsVO> getUserOrders() {
        List<UserOrder> userOrders = new ArrayList<>();
        userOrderRepository.findAll().forEach(userOrders::add);
        return getUserOrdersFromBOs(userOrders);
    }

}
