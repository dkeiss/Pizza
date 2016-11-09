package pizza.service.common;

import pizza.domain.order.UserOrder;
import pizza.domain.order.UserOrderAdditional;
import pizza.domain.product.ProductCatalog;
import pizza.domain.product.ProductCategory;
import pizza.domain.user.User;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.order.UserOrderVO;
import pizza.vo.product.additional.AdditionalInfoVO;
import pizza.vo.product.menu.ProductCatalogVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Daniel Keiss on 08.11.2016.
 */
public class UserOrderBusinessToValueObjectConverter {

    public static List<UserOrderDetailsVO> getUserOrdersFromBOs(List<UserOrder> userOrders) {
        return userOrders.stream().map(UserOrderBusinessToValueObjectConverter::getUserOrderFromBO).collect(Collectors.toList());
    }

    public static UserOrderDetailsVO getUserOrderFromBO(UserOrder userOrder) {
        UserOrderDetailsVO userOrderDetailsVO = new UserOrderDetailsVO();
        userOrderDetailsVO.setUserOrderId(userOrder.getUserOrderId());
        User user = userOrder.getUser();
        userOrderDetailsVO.setUserId(user.getUserId());
        userOrderDetailsVO.setFirstName(user.getFirstName());
        userOrderDetailsVO.setLastName(user.getLastName());
        userOrderDetailsVO.setProductId(userOrder.getProduct().getProductId());
        userOrderDetailsVO.setProductVariationId(userOrder.getProductVariation().getProductVariationId());
        userOrderDetailsVO.setAdditionalIds(getAdditionalIdsFromBOs(userOrder.getUserOrderAdditionals()));
        userOrderDetailsVO.setAdditionals(getAdditionalInfosFromBOs(userOrder.getUserOrderAdditionals()));
        userOrderDetailsVO.setSum(userOrder.getAmount());
        userOrderDetailsVO.setPaid(userOrder.getPaid());
        return userOrderDetailsVO;
    }

    private static List<AdditionalInfoVO> getAdditionalInfosFromBOs(List<UserOrderAdditional> userOrderAdditionals) {
        return userOrderAdditionals.stream().map(UserOrderBusinessToValueObjectConverter::getAdditionalInfoFromBOs).collect(Collectors.toList());
    }

    private static AdditionalInfoVO getAdditionalInfoFromBOs(UserOrderAdditional userOrderAdditional) {
        AdditionalInfoVO additionalInfoVO = new AdditionalInfoVO();
        additionalInfoVO.setAdditionalId(userOrderAdditional.getAdditional().getAdditionalId());
        additionalInfoVO.setDescription(userOrderAdditional.getAdditional().getDescription());
        return additionalInfoVO;
    }

    private static List<Integer> getAdditionalIdsFromBOs(List<UserOrderAdditional> userOrderAdditionals) {
        return userOrderAdditionals.stream().map(userOrderAdditional -> userOrderAdditional.getAdditional().getAdditionalId()).collect(Collectors.toList());
    }

}