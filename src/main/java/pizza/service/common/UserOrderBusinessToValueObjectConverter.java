package pizza.service.common;

import pizza.domain.order.UserOrder;
import pizza.domain.order.UserOrderAdditionals;
import pizza.domain.user.User;
import pizza.vo.order.UserOrderDetailsVO;
import pizza.vo.product.additional.AdditionalInfoVO;

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

    private static List<AdditionalInfoVO> getAdditionalInfosFromBOs(List<UserOrderAdditionals> userOrderAdditionalses) {
        return userOrderAdditionalses.stream().map(UserOrderBusinessToValueObjectConverter::getAdditionalInfoFromBOs).collect(Collectors.toList());
    }

    private static AdditionalInfoVO getAdditionalInfoFromBOs(UserOrderAdditionals userOrderAdditionals) {
        AdditionalInfoVO additionalInfoVO = new AdditionalInfoVO();
        additionalInfoVO.setAdditionalId(userOrderAdditionals.getAdditional().getAdditionalId());
        additionalInfoVO.setDescription(userOrderAdditionals.getAdditional().getDescription());
        return additionalInfoVO;
    }

    private static List<Integer> getAdditionalIdsFromBOs(List<UserOrderAdditionals> userOrderAdditionalses) {
        return userOrderAdditionalses.stream().map(userOrderAdditional -> userOrderAdditional.getAdditional().getAdditionalId()).collect(Collectors.toList());
    }

}