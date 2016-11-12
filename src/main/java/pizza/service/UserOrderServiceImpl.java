package pizza.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pizza.domain.order.BulkOrder;
import pizza.domain.order.UserOrder;
import pizza.domain.order.UserOrderAdditional;
import pizza.domain.product.Product;
import pizza.domain.product.ProductVariation;
import pizza.domain.product.additional.Additional;
import pizza.domain.product.additional.AdditionalPrice;
import pizza.domain.user.User;
import pizza.repositories.*;
import pizza.service.exception.*;
import pizza.vo.order.UserOrderAdditionalVO;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderPaidVO;
import pizza.vo.order.UserOrderVO;

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

        UserOrder userOrder = new UserOrder();
        userOrder.setUser(user);
        userOrder.setBulkOrder(activeBulkOrder);
        userOrder.setProduct(product);
        userOrder.setProductVariation(productVariation);
        List<UserOrderAdditional> userOrderAdditionals = createUserOrderAdditionals(userOrderVO, userOrder);
        userOrder.setUserOrderAdditionals(userOrderAdditionals);
        userOrder.setAmount(calculateAmount(productVariation, userOrderAdditionals));
        userOrder.setPaid(false);
        userOrder.setNumber(userOrderVO.getNumber());
        userOrder.setCreationDate(new Date());

        userOrderRepository.save(userOrder);

        return getUserOrderFromBO(userOrder);
    }

    private BigDecimal calculateAmount(ProductVariation productVariation, List<UserOrderAdditional> userOrderAdditionalses) {
        BigDecimal amount = productVariation.getPrice();
        if (userOrderAdditionalses != null) {
            for (UserOrderAdditional userOrderAdditional : userOrderAdditionalses) {
                AdditionalPrice additionalPrice = userOrderAdditional.getAdditionalPrice();
                if (additionalPrice == null) {
                    continue;
                }
                amount = amount.add(additionalPrice.getPrice());
            }
        }
        return amount;
    }

    private List<UserOrderAdditional> createUserOrderAdditionals(UserOrderVO userOrderVO, UserOrder userOrder) {
        if (userOrderVO.getUserOrderAdditionals() == null || userOrderVO.getUserOrderAdditionals().isEmpty()) {
            return null;
        }
        List<UserOrderAdditional> userOrderAdditions = new ArrayList<>();
        for (UserOrderAdditionalVO userOrderAdditionalVO : userOrderVO.getUserOrderAdditionals()) {
            Additional additional = additionalService.findAdditional(userOrderAdditionalVO.getAdditionalId());
            if (additional == null) {
                throw new UserOrderAdditionalNotFoundException();
            }
            AdditionalPrice additionalPrice = additionalService.findAdditionalPrice(userOrderAdditionalVO.getAdditionalPriceId());
            if (additionalPrice == null) {
                throw new UserOrderAdditionalPriceNotFoundException();
            }
            UserOrderAdditional userOrderAdditional = new UserOrderAdditional();
            userOrderAdditional.setUserOrder(userOrder);
            userOrderAdditional.setAdditional(additional);
            userOrderAdditional.setAdditionalPrice(additionalPrice);
            userOrderAdditional.setCreationDate(new Date());
            userOrderAdditions.add(userOrderAdditional);
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

    @Override
    public UserOrderDetailsVO getUserOrder(Integer userOrderId) {
        UserOrder userOrder = getUserOrderBO(userOrderId);
        return getUserOrderFromBO(userOrder);
    }

    public UserOrder getUserOrderBO(Integer userOrderId) {
        UserOrder userOrder = userOrderRepository.findOne(userOrderId);
        if (userOrder == null) {
            throw new NotFoundException();
        }
        return userOrder;
    }

    @Override
    public void setUserOrderPaid(Integer userOrderId, UserOrderPaidVO userOrderPaidVO) {
        UserOrder userOrder = getUserOrderBO(userOrderId);
        userOrder.setPaid(userOrderPaidVO.getPaid());
        userOrderRepository.save(userOrder);
    }

    @Override
    public void deleteUserOrder(Integer userOrderId) {
        try {
            userOrderRepository.delete(userOrderId);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

}
